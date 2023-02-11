package com.gdscssu.garbagecollector.domain.basket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.gdscssu.garbagecollector.domain.entity.Location;
import com.gdscssu.garbagecollector.domain.trash.entity.Trash;
import com.gdscssu.garbagecollector.global.StatusType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
public class Basket {
    @Column(name = "basketId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double lat;
    @Column(nullable = false)
    private double lng;

    @Enumerated(EnumType.STRING)
    @Column(name = "type1")
    private BasketType1 type1;

    @Enumerated(EnumType.STRING)

    @Column(name = "type2")

    private BasketType2 type2;
    @Column(nullable = false)
    private String detailAddress;


    // 1:N (Basket-Trash)
    @OneToMany(mappedBy = "basket")
    private List<Trash> trashes;

    //N:1 (Basket-Location)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locationCode")
    private Location location;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Enumerated(EnumType.STRING)
    private StatusType status;


}
