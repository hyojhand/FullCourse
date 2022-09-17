package com.ssafy.fullcourse.domain.place.application;

import com.ssafy.fullcourse.domain.place.dto.CultureDetailRes;
import com.ssafy.fullcourse.domain.place.dto.ListReq;
import com.ssafy.fullcourse.domain.place.dto.PlaceRes;
import com.ssafy.fullcourse.domain.place.entity.Culture;
import com.ssafy.fullcourse.domain.place.entity.CultureLike;
import com.ssafy.fullcourse.domain.place.repository.CultureLikeRepository;
import com.ssafy.fullcourse.domain.place.repository.CultureRepository;
import com.ssafy.fullcourse.domain.review.exception.PlaceNotFoundException;
import com.ssafy.fullcourse.domain.user.entity.User;
import com.ssafy.fullcourse.domain.user.exception.UserNotFoundException;
import com.ssafy.fullcourse.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CultureServiceImpl implements CultureService{

    @Autowired
    CultureRepository cultureRepository;
    @Autowired
    CultureLikeRepository cultureLikeRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Page<PlaceRes> getCultureList(ListReq listReq, Pageable pageable) throws Exception {
        Page<Culture> page = cultureRepository.findAll(pageable);
        return page.map(PlaceRes::new);
    }

    @Override
    public CultureDetailRes getCultureDetail(Long placeId) throws Exception {
        return cultureRepository.findByPlaceId(placeId).get().toDetailDto();
    }

    @Override
    @Transactional
    public boolean cultureLike(Long placeId, Long userId) throws Exception {
        User user = userRepository.findById(userId).get();
        Culture culture = cultureRepository.findByPlaceId(placeId).get();

        if(user == null){
            throw new UserNotFoundException();
        }if(culture == null){
            throw new PlaceNotFoundException();
        }

        Optional<CultureLike> cultureLike = cultureLikeRepository.findByUserAndPlace(user, culture);

        if(cultureLike.isPresent()){
            cultureLikeRepository.deleteById(cultureLike.get().getLikeId());
        }else{
            cultureLikeRepository.save(CultureLike.builder().user(user).place(culture).build());
        }

        return true;
    }
}