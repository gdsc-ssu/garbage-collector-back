package com.gdscssu.garbagecollector.domain.ranking.entity;


import com.gdscssu.garbagecollector.global.config.BaseEntity;
import com.gdscssu.garbagecollector.global.config.StatusType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
public class Ranking extends BaseEntity {
    @Column(name = "rankingId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long totalRank;

    @Column
    private Long regionRank;

    @Column
    private Long score;

}
