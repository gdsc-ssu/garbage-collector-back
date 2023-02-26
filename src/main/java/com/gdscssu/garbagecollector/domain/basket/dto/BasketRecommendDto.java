package com.gdscssu.garbagecollector.domain.basket.dto;

import com.gdscssu.garbagecollector.domain.trash.entity.TrashType2;
import lombok.Data;

@Data
public class BasketRecommendDto {

    private Double lng;
    private Double lat;
    private String type;
}
