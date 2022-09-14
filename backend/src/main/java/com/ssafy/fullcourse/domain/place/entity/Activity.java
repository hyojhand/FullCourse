package com.ssafy.fullcourse.domain.place.entity;

import com.ssafy.fullcourse.domain.review.entity.ActivityReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity extends BasePlace{
    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 100)
    private String subtitle;

    @Column(nullable = false)
    private Float lat;

    @Column(nullable = false)
    private Float lng;

    @Column(length = 20)
    private String tel;

    @Column(nullable = false, length = 20)
    private String gugun;

    @Column(nullable = false, length = 30)
    private String place;

    @Column(nullable = false,length = 100)
    private String imgUrl;

    @Column(length = 30)
    private String holiday;

    @Column(length = 30)
    private String openTime;

    @Column(length = 200)
    private String transport;

    @Column(nullable = false)
    private Long addedCnt;

    @Column(nullable = false)
    private Long reviewCnt;

    @Column(nullable = false)
    private Long likeCnt;

    @Builder.Default
    @OneToMany(mappedBy = "place", cascade = CascadeType.REMOVE)
    List<ActivityReview> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "activity", cascade = CascadeType.REMOVE)
    List<ActivityLike> likes = new ArrayList<>();
}
