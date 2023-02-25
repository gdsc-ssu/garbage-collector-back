package com.gdscssu.garbagecollector.domain.trash.dto;

import lombok.Data;

@Data
public class PostUserDumpReq {
    private String trashType1;
    private String trashType2;

    private Long basketId;
}
