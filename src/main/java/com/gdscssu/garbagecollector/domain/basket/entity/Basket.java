package com.gdscssu.garbagecollector.domain.basket.entity;

import com.gdscssu.garbagecollector.domain.entity.Location;
import com.gdscssu.garbagecollector.domain.trash.entity.Trash;
import com.gdscssu.garbagecollector.global.config.BaseEntity;
import com.gdscssu.garbagecollector.global.config.StatusType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
public class Basket extends BaseEntity {
    @Column(name = "basketId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double lat;
    @Column(nullable = false)
    private double lng;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BasketType type;
    @Column
    private String location1;
    @Column
    private String location2;
    @Column
    private String location3;


    @Column(nullable = false)
    private String detailAddress;


    // 1:N (Basket-Trash)
    @OneToMany(mappedBy = "basket")
    private List<Trash> trashes;

    //N:1 (Basket-Location)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locationCode")
    private Location location;




}
