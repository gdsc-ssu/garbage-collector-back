package com.gdscssu.garbagecollector.domain.collection.dto;

import com.gdscssu.garbagecollector.domain.collection.entity.CollectionType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(access = AccessLevel.PUBLIC)
public class UpdateCollectionResponseDTO {
    private CollectionType type;

    private Integer level;

    private Integer exp;
}
