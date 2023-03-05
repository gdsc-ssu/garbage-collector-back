package com.gdscssu.garbagecollector.domain.score.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(access = AccessLevel.PUBLIC)
public class GetRankingResponseDTO {
    private final Long id;
    private final String nickname;

    private final String profileImg;
    private final Long totalScore;
}
