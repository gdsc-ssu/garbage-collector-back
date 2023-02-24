package com.gdscssu.garbagecollector.domain.score.entity;


import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import com.gdscssu.garbagecollector.domain.user.entity.User;
import com.gdscssu.garbagecollector.global.config.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Entity
public class Score extends BaseEntity {
    @Id
    @Column(name = "scoreId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @OneToOne(fetch = LAZY, cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @Column(name = "score")
    private int score;
}
