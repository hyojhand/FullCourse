package com.ssafy.fullcourse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String sido;

    @Column(nullable = false)
    private String gugun;

    @Column(nullable = false)
    private Float lat;

    @Column(nullable = false)
    private Float lng;

    @Column(nullable = true)
    private String url;

    @Column(nullable = true)
    private String tel;

    @Column(nullable = false)
    private Long count;
}