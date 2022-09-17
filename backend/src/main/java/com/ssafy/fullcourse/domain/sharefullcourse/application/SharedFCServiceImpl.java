package com.ssafy.fullcourse.domain.sharefullcourse.application;

import com.ssafy.fullcourse.domain.sharefullcourse.dto.*;
import com.ssafy.fullcourse.domain.sharefullcourse.entity.SharedFCLike;
import com.ssafy.fullcourse.domain.sharefullcourse.entity.SharedFCTag;
import com.ssafy.fullcourse.domain.sharefullcourse.entity.SharedFullCourse;
import com.ssafy.fullcourse.domain.sharefullcourse.exception.AlreadyExistException;
import com.ssafy.fullcourse.domain.sharefullcourse.exception.SharedFCNotFoundException;
import com.ssafy.fullcourse.domain.sharefullcourse.mapper.SharedFCMapper;
import com.ssafy.fullcourse.domain.sharefullcourse.repository.SharedFCLikeRepository;
import com.ssafy.fullcourse.domain.sharefullcourse.repository.SharedFCRepository;
import com.ssafy.fullcourse.domain.sharefullcourse.repository.SharedFCTagRepository;
import com.ssafy.fullcourse.domain.user.entity.User;
import com.ssafy.fullcourse.global.error.ServerError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SharedFCServiceImpl implements SharedFCService{

    @Autowired
    SharedFCRepository sharedFCRepository;
    @Autowired
    SharedFCTagRepository sharedFCTagRepository;
    @Autowired
    SharedFCLikeRepository sharedFCLikeRepository;

    // 공유 풀코스 생성
    @Override
    @Transactional
    public Long createSharedFC(SharedFCDto sharedFCDto, List<SharedFCTagDto> tags) {
        Optional<SharedFullCourse> opt = Optional.ofNullable(sharedFCRepository.findByFullCourseFcId(sharedFCDto.getFullCourse().getFcId()));

        if(opt.isPresent()) throw new AlreadyExistException("이미 공유한 풀코스 입니다.");

        SharedFullCourse sharedFullCourse = SharedFCMapper.MAPPER.toEntity(sharedFCDto);

        tagDtoE(tags,sharedFullCourse);

        SharedFullCourse saved = sharedFCRepository.save(sharedFullCourse);
        if(saved != null) return saved.getSharedFcId(); // 생성 성공
        else throw new ServerError("공유 풀코스 생성 중 알 수 없는 에러가 발생했습니다.");

    }

    // 공유 풀코스 상세 조회
    @Override
    @Transactional
    public SharedFCGetRes detailSharedFC(Long sharedFcId) {
        Optional<SharedFullCourse> opt = Optional.ofNullable(sharedFCRepository.findBySharedFcId(sharedFcId));
        SharedFullCourse sharedFullCourse = opt.orElseThrow(()->new SharedFCNotFoundException());
        SharedFCGetRes res = SharedFCGetRes.of(sharedFullCourse);
        sharedFCRepository.plusViewCnt(sharedFcId);
        return res;
    }

    // 공유 풀코스 상세 수정
    @Override
    @Transactional
    public Long updateSharedFC(SharedFCDto sharedFCDto, List<SharedFCTagDto> tags) {
        Optional<SharedFullCourse> opt = Optional.ofNullable(sharedFCRepository.findBySharedFcId(sharedFCDto.getSharedFcId()));

        SharedFullCourse now = opt.orElseThrow(()->new SharedFCNotFoundException());

        for(int i = 0 ; i < now.getSharedFCTags().size();i++) {
            now.getSharedFCTags().remove(i);
        }
        SharedFullCourse sharedFullCourse = SharedFullCourse.sharedFCUpdate(sharedFCDto,now);

        tagDtoE(tags,sharedFullCourse);

        SharedFullCourse saved = sharedFCRepository.save(sharedFullCourse);

        if(saved != null) return saved.getSharedFcId(); // 수정 성공
        else throw new ServerError("상세 풀코스 수정 중 오류가 발생했습니다."); // 수정 중 오류

    }

    // 공유 풀코스 삭제
    @Override
    @Transactional
    public void deleteSharedFC(Long sharedFdId) {
        SharedFullCourse saved =sharedFCRepository.findBySharedFcId(sharedFdId);

        if(saved == null) throw new SharedFCNotFoundException();

        sharedFCRepository.delete(SharedFullCourse.builder().sharedFcId(sharedFdId).build());

    }


    @Override
    @Transactional
    public int likeSharedFC(Long sharedId, User user) {

        SharedFullCourse sharedFullCourse = sharedFCRepository.findBySharedFcId(sharedId);
        if(sharedFullCourse == null) throw new SharedFCNotFoundException();
        // 좋아요 확인
        Optional<SharedFCLike> opt = Optional.ofNullable(sharedFCLikeRepository.findByUser_UserIdAndSharedFullCourse_SharedFcId(user.getUserId(), sharedId));

        if(opt.isPresent()){ // 좋아요 취소
            sharedFCLikeRepository.delete(opt.get());
            sharedFCRepository.minusLikeCnt(sharedId);
            return 0;
        }else{ // 좋아요

            sharedFCLikeRepository.save(SharedFCLike.builder()
                    .user(user)
                    .sharedFullCourse(sharedFullCourse).build());
            sharedFCRepository.plusLikeCnt(sharedId);
            return 1;
        }
    }

    public void tagDtoE(List<SharedFCTagDto> tags, SharedFullCourse sharedFullCourse){
        for(SharedFCTagDto tag : tags) {
            SharedFCTag sharedFCTag = SharedFCTag.of(tag,sharedFullCourse);
            sharedFullCourse.getSharedFCTags().add(sharedFCTag);
            sharedFCTag.setSharedFullCourse(sharedFullCourse);
        }
    }

}