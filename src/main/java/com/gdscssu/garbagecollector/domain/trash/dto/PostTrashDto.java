package com.gdscssu.garbagecollector.domain.trash.dto;

import com.gdscssu.garbagecollector.domain.trash.entity.TrashType1;
import com.gdscssu.garbagecollector.domain.trash.entity.TrashType2;
import lombok.Data;

@Data
public class PostTrashDto {

    private Long basketId;

    private TrashType1 trashType1;

    private TrashType2 trashType2;
}
