package com.gdscssu.garbagecollector.domain.score.dto;

import com.gdscssu.garbagecollector.domain.collection.entity.CollectionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(access = AccessLevel.PUBLIC)
public class GetScoreHistoryResponseDTO {
    private final CollectionType type;
    private final Integer point;
    private final Long basketId;
    private final LocalDateTime dateTime;
}
