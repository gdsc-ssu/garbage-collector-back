package com.gdscssu.garbagecollector.domain.collection.dto;

import com.gdscssu.garbagecollector.domain.collection.entity.CollectionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCollectionRequestDTO {
    @NotNull
    private Integer level;

    @NotNull
    private Integer exp;
}
