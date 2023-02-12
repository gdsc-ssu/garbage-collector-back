package com.gdscssu.garbagecollector.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.gdscssu.garbagecollector.domain.ranking.entity.Ranking;
import com.gdscssu.garbagecollector.domain.trash.entity.Trash;
import com.gdscssu.garbagecollector.global.config.BaseEntity;
import com.gdscssu.garbagecollector.global.config.StatusType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uid;

    @Column
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column
    private String profileImg;

    @Column
    private String refreshToken;

    // OneToOne(User-Ranking) User에 연관관계가 있는 형태 (N+1을 막기 위해 단방향 걸어줌)
    @OneToOne
    @JoinColumn(name = "id")
    private Ranking ranking;

    // 1:N (User-Trash)
    @OneToMany(mappedBy = "user")
    private List<Trash> trashes;






    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
