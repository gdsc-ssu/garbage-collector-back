package com.gdscssu.garbagecollector.domain.basket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostBasketMarkingRes {

    private String lng;
    private String lat;

    private String basketName;

    //유저가 해당 쓰레기통에서 쓰레기를 버린 횟수
    private Integer userTrash;

    // 유저의 위치와 쓰레기통의 거리차이는 프론트에서


}
