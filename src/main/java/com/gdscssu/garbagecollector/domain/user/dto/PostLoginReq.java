package com.gdscssu.garbagecollector.domain.user.dto;

import lombok.Data;

@Data
public class PostLoginReq {

    private String userName;
    private String userEmail;
    private String userProfileImg;
    private String accessToken;
}
