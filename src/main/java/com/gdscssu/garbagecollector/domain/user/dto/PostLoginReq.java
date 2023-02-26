package com.gdscssu.garbagecollector.domain.user.dto;

import lombok.Data;

@Data
public class PostLoginReq {

    private String nickName;
    private String email;
    private String profileImg;
    private String accessToken;
}
