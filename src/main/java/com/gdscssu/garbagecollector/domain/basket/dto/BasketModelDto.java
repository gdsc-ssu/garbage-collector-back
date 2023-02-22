package com.gdscssu.garbagecollector.domain.basket.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BasketModelDto {

    private long basketId;
    private double lng;
    private double lat;

    private String basketName;

    //유저가 해당 쓰레기통에서 쓰레기를 버린 횟수
    private Integer userTrash;

    private LocalDateTime updatedAt;


}
