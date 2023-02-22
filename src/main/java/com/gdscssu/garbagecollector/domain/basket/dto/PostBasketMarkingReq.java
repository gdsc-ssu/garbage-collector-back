package com.gdscssu.garbagecollector.domain.basket.dto;

import lombok.Data;

// 유저의 위치 정보 받아옴
@Data
public class PostBasketMarkingReq {

    private Double lat1;
    private Double lng1;
    private Double lat2;
    private Double lng2;
}
