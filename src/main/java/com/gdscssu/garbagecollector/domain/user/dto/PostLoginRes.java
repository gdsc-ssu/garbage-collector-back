package com.gdscssu.garbagecollector.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostLoginRes {
    private Long id;
    private String profileImg;
    private String nickname;

    private String email;
    private int general;
    private int plastic;
    private int can;
    private int glass;

    private int paper;
    private String accessToken;
    private String refreshToken;
}
