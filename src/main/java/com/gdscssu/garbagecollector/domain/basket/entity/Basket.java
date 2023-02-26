package com.gdscssu.garbagecollector.domain.basket.entity;

import com.gdscssu.garbagecollector.domain.location.entity.Location;
import com.gdscssu.garbagecollector.domain.trash.entity.Trash;
import com.gdscssu.garbagecollector.global.config.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
public class Basket extends BaseEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double lat;
    @Column
    private Double lng;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BasketType type;
    @Column(name="location1")
    private String location1;
    @Column(name="location2")
    private String location2;
    @Column(name="location3")
    private String location3;


    @Column(name = "detailAddress" ,nullable = false)
    private String detailAddress;


    // 1:N (Basket-Trash)
    @OneToMany(mappedBy = "basket")
    private List<Trash> trashes;

    //N:1 (Basket-Location)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locationCode")
    private Location location;

    // N(report) : 1(basket)

    @OneToMany(mappedBy = "basket")
    private List<Report> reportList;





}
