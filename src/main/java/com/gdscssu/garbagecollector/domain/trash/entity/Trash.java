package com.gdscssu.garbagecollector.domain.trash.entity;

import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import com.gdscssu.garbagecollector.domain.user.entity.User;
import com.gdscssu.garbagecollector.global.config.BaseEntity;
import com.gdscssu.garbagecollector.global.config.StatusType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
public class Trash extends BaseEntity {

    @Column(name = "trashId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private TrashType1 type1;

    @Enumerated(EnumType.STRING)
    private TrashType2 type2;

    @Column(nullable = false)
    private String name;

    //N:1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId")
    private User user;

    //N:1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "basketId")
    private Basket basket;




}
