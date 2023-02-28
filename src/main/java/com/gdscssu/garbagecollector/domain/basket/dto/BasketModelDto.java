package com.gdscssu.garbagecollector.domain.basket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String detailAddress;

    //유저가 해당 쓰레기통에서 쓰레기를 버린 횟수
    private Integer userTrash;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;


}
