package com.gdscssu.garbagecollector.domain.collection.entity;

import com.gdscssu.garbagecollector.domain.trash.entity.Trash;
import com.gdscssu.garbagecollector.domain.user.entity.User;
import com.gdscssu.garbagecollector.global.config.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Entity
@Getter
public class Collection extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CollectionType type;

    @Column(name = "level")
    private Integer level;

    @Column(name = "exp")
    private Integer exp;

    // 1:N
    @OneToMany(mappedBy = "collection")
    private List<Trash> trashList;

    @Builder
    public Collection(User user, CollectionType type, Integer level, Integer exp) {
        this.user = user;
        this.type = type;
        this.level = level;
        this.exp = exp;
    }

    public void update(Integer level, Integer exp) {
        this.level = level;
        this.exp = exp;
    }
}
