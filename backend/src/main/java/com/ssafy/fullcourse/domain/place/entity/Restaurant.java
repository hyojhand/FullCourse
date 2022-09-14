package com.ssafy.fullcourse.domain.place.entity;

import com.ssafy.fullcourse.domain.place.dto.PlaceRes;
import com.ssafy.fullcourse.domain.review.entity.RestaurantReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BasePlace {
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false)
    private Float lat;

    @Column(nullable = false)
    private Float lng;

    @Column(length = 20)
    private String tel;

    @Column(nullable = false, length = 20)
    private String category; // 카테고리

    @Column(nullable = false, length = 500)
    private String intro; // 소개

    @Column(length = 20)
    private String holiday;

    @Column(length = 20)
    private String openTime;

    @Column(length = 100)
    private String url; // 홈페이지

    @Column(nullable = false)
    private Float stgScore; // 수용태세지수

    @Column(length = 50)
    private String award; // 어워드

    private Float naverScore;

    @Column(length = 100)
    private String imgUrl;

    @Column(nullable = false)
    private Long addedCnt;

    @Column(nullable = false)
    private Long reviewCnt;

    @Column(nullable = false)
    private Long likeCnt;

    @Builder.Default
    @OneToMany(mappedBy = "place", cascade = CascadeType.REMOVE)
    List<RestaurantReview> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    List<RestaurantLike> likes = new ArrayList<>();

    public PlaceRes toDto(){
        PlaceRes res = new PlaceRes();
        res.setImgUrl(this.getImgUrl());
        res.setLat(this.getLat());
        res.setName(this.getName());
        res.setLng(this.getLng());
        res.setLikeCnt(this.getLikeCnt());
        res.setPlaceId(this.getPlaceId());
        res.setReviewCnt(this.getReviewCnt());
        return res;
    }


}
