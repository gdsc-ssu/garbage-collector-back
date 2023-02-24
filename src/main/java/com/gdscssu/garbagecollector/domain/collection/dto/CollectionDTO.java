package com.gdscssu.garbagecollector.domain.collection.dto;

import com.gdscssu.garbagecollector.domain.collection.entity.CollectionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(access = AccessLevel.PUBLIC)
public class CollectionDTO {
    private final CollectionType type;
    private final Integer level;
    private final Integer exp;
}
