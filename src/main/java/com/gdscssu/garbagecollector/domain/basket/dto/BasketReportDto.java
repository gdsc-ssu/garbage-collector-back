package com.gdscssu.garbagecollector.domain.basket.dto;

import com.gdscssu.garbagecollector.domain.basket.entity.ReportType;
import lombok.Data;

@Data
public class BasketReportDto {

    private String reportType;
    private Long basketId;
}
