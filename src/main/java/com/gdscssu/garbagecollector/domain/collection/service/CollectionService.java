package com.gdscssu.garbagecollector.domain.collection.service;

import com.gdscssu.garbagecollector.domain.collection.dto.CollectionDTO;
import com.gdscssu.garbagecollector.domain.collection.dto.CollectionResponseDTO;
import com.gdscssu.garbagecollector.domain.collection.entity.Collection;
import com.gdscssu.garbagecollector.domain.collection.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;

    public CollectionResponseDTO getCollection(Long id) {
        List<Collection> collections = collectionRepository.findAllByUserId(id);

        List<CollectionDTO> collectionDtos = collections.stream().map(
                collection -> CollectionDTO
                        .builder()
                        .type(collection.getType())
                        .level(collection.getLevel())
                        .exp(collection.getExp())
                        .build()
        ).collect(Collectors.toList());

        return CollectionResponseDTO
                .builder()
                .userId(id)
                .collections(collectionDtos)
                .build();
    }
}
