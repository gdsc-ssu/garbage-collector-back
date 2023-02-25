package com.gdscssu.garbagecollector.domain.trash.entity;

import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import com.gdscssu.garbagecollector.domain.collection.entity.Collection;
import com.gdscssu.garbagecollector.domain.user.entity.User;
import com.gdscssu.garbagecollector.global.config.BaseEntity;
import com.gdscssu.garbagecollector.global.config.StatusType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class Trash extends BaseEntity {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name="type1")
    private TrashType1 type1;

    @Enumerated(EnumType.STRING)
    @Column(name="type2")
    private TrashType2 type2;


    //N:1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId")
    private User user;

    //N:1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "basketId")
    private Basket basket;

    // 1(collection) : N(trash)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="collectionId")
    private Collection collection;



    @Builder
    public Trash(TrashType1 type1,TrashType2 type2,User user,Basket basket,Collection collection){
        this.type1=type1;
        this.type2=type2;
        this.user=user;
        this.basket=basket;
        this.collection=collection;

    }


    public Trash() {

    }
}
