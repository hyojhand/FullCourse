package com.ssafy.fullcourse.domain.place.entity;

import com.ssafy.fullcourse.domain.place.dto.ActivityDetailRes;
import com.ssafy.fullcourse.domain.place.entity.baseentity.BasePlace;
import com.ssafy.fullcourse.domain.review.entity.ActivityReview;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity extends BasePlace {
    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 300)
    private String subtitle;

    @Column(nullable = false)
    private Float lat;

    @Column(nullable = false)
    private Float lng;

    @Column(length = 20)
    private String tel;

    @Column(nullable = false, length = 20)
    private String gugun;

    @Column(nullable = false, length = 30, name = "place")
    private String address;

    @Column(nullable = false, length = 300)
    private String imgUrl;

    @Column(length = 30)
    private String holiday;

    @Column(length = 100)
    private String openTime;

    @Column(length = 200)
    private String transport;

    @Column(nullable = false, length = 5000)
    private String content;

    @Column(nullable = false)
    private Long addedCnt = 0L;

    @Column(nullable = false)
    private Long reviewCnt = 0L;

    @Column(nullable = false)
    private Long likeCnt = 0L;

    @Column(nullable = false)
    private Float reviewScore = 0F;

    @OneToMany(mappedBy = "place", cascade = CascadeType.REMOVE)
    List<ActivityReview> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.REMOVE)
    List<ActivityLike> likes = new ArrayList<>();


    public ActivityDetailRes toDetailDto() {
        ActivityDetailRes res = new ActivityDetailRes();
        res.setName(this.getName());
        res.setSubtitle(this.getSubtitle());
        res.setLat(this.getLat());
        res.setLng(this.getLng());
        res.setTel(this.getTel());
        res.setGugun(this.getGugun());
        res.setAddress(this.getAddress());
        res.setImgUrl(this.getImgUrl());
        res.setHoliday(this.getHoliday());
        res.setOpenTime(this.getOpenTime());
        res.setContent(this.getContent());
        res.setTransport(this.getTransport());
        res.setAddedCnt(this.getAddedCnt());
        res.setReviewCnt(this.getReviewCnt());
        res.setLikeCnt(this.getLikeCnt());
        res.setReviewScore(this.getReviewScore());
        return res;
    }

    public void updateReviewScore(Float reviewScore) {
        this.reviewScore = reviewScore;
    }

}
