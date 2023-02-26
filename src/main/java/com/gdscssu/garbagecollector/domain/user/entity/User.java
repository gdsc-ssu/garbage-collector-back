package com.gdscssu.garbagecollector.domain.user.entity;

import com.gdscssu.garbagecollector.domain.score.entity.Score;
import com.gdscssu.garbagecollector.domain.trash.entity.Trash;
import com.gdscssu.garbagecollector.global.config.BaseEntity;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nickname")
    private String nickname;

    @Column(name="email",nullable = false)
    private String email;

    @Column(name="profileImg")
    private String profileImg;

    @Column(name="refreshToken")
    private String refreshToken;

    // OneToOne(User-Ranking) User에 연관관계가 있는 형태 (N+1을 막기 위해 단방향 걸어줌)
    @OneToOne
    @JoinColumn(name = "scoreId")
    private Score score;

    // 1:N (User-Trash)
    @OneToMany(mappedBy = "user")
    private List<Trash> trashes;




    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Builder
    public User(String nickname,String email,String profileImg){
        this.nickname=nickname;
        this.email=email;
        this.profileImg=profileImg;
    }

    public User() {

    }

    public User(Claims claims) {
        super();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
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