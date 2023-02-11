package com.gdscssu.garbagecollector.domain.entity;


import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import com.gdscssu.garbagecollector.global.StatusType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
public class Location {


    @Column(name = "locationCode")
    @Id
    private String code;

    @Column(nullable = false)
    private String location1;

    @Column(nullable = false)
    private String location2;

    @Column(nullable = false)
    private String location3;

    // 1:N (Location-Basket)
    @OneToMany(mappedBy = "location")
    private List<Basket> baskets;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Enumerated(EnumType.STRING)
    private StatusType status;
}
