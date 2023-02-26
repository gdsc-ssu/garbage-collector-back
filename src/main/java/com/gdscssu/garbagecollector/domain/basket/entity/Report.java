package com.gdscssu.garbagecollector.domain.basket.entity;

import com.gdscssu.garbagecollector.global.config.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Report extends BaseEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="reportType")
    private ReportType reportType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="basketId")
    private Basket basket;

    @Builder
    public Report(ReportType reportType,Basket basket){
        this.reportType=reportType;
        this.basket=basket;
    }

    public Report() {

    }
}
