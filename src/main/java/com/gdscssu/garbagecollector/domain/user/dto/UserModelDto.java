package com.gdscssu.garbagecollector.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModelDto {

    private Long id;
    private String profileUrl;
    private String nickname;

    private String email;
    private int general;
    private int plastic;
    private int can;
    private int glass;

    private int paper;
}
