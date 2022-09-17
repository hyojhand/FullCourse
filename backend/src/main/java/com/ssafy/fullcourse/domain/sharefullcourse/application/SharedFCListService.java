package com.ssafy.fullcourse.domain.sharefullcourse.application;

import com.ssafy.fullcourse.domain.sharefullcourse.dto.SharedFCDto;
import com.ssafy.fullcourse.domain.sharefullcourse.dto.SharedFCListDto;
import com.ssafy.fullcourse.domain.sharefullcourse.dto.SharedFCTagDto;
import com.ssafy.fullcourse.domain.sharefullcourse.entity.SharedFCLike;
import com.ssafy.fullcourse.domain.sharefullcourse.entity.SharedFCTag;
import com.ssafy.fullcourse.domain.sharefullcourse.entity.SharedFullCourse;
import com.ssafy.fullcourse.domain.sharefullcourse.repository.SharedFCLikeRepository;
import com.ssafy.fullcourse.domain.sharefullcourse.repository.SharedFCRepository;
import com.ssafy.fullcourse.domain.user.entity.User;
import com.ssafy.fullcourse.domain.user.repository.UserRepository;
import com.ssafy.fullcourse.global.model.BaseResponseBody;
import com.ssafy.fullcourse.global.model.PageDto;
import com.ssafy.fullcourse.global.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SharedFCListService {

    private final SharedFCRepository sharedFCRepository;
    private final SharedFCLikeRepository sharedFCLikeRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    private PageRequest getPageRequest(PageDto pageDto) {
        if (pageDto.getSort() == null) return PageRequest.of(pageDto.getPage(), pageDto.getSize());
        else return PageRequest.of(pageDto.getPage(), pageDto.getSize(), Sort.Direction.DESC, pageDto.getSort());
    }

    public Page<SharedFCListDto> getSharedFCList(PageDto pageDto) {

        Page<SharedFullCourse> page;
        if(pageDto.getKeyword() == null) {
            PageRequest pageRequest = getPageRequest(pageDto);
            page = sharedFCRepository.findAll(pageRequest);
        } else {
            PageRequest pageRequest = getPageRequest(pageDto);
            page = sharedFCRepository.findFCListByTitleContains(pageDto.getKeyword(), pageRequest);
        }
        return page.map(share -> new SharedFCListDto(share, share.getSharedFCTags().stream().map(SharedFCTagDto::new).collect(Collectors.toList())));
    }

    public Page<SharedFCListDto> getSharedFCLikeList(HttpServletRequest request, PageDto pageDto) {
        String token = request.getHeader("access-token");
        if (!tokenProvider.validateToken(token)) {
            return null;
        }

        String userEmail = String.valueOf(tokenProvider.getPayload(token).get("sub"));
        User findUser = userRepository.findByEmail(userEmail).get();


        /**
         * 고민
         */
//        Page<SharedFCLike> page;
//        if(pageDto.getKeyword() == null) {
//            PageRequest pageRequest = getPageRequest(pageDto);
//            page = sharedFCLikeRepository.findFCLikeByUser(findUser, pageRequest);
//        } else {
//            PageRequest pageRequest = getPageRequest(pageDto);
//            page = sharedFCLikeRepository.findFCLikeByUser(findUser, pageDto.getKeyword(), pageRequest);
//        }

        PageRequest pageRequest = getPageRequest(pageDto);
        Page<SharedFCLike> page = sharedFCLikeRepository.findFCLikeByUser(findUser, pageRequest);


        return page.map(share -> new SharedFCListDto(share.getSharedFullCourse(),
                share.getSharedFullCourse().getSharedFCTags().stream().map(SharedFCTagDto::new).collect(Collectors.toList())));
    }




}