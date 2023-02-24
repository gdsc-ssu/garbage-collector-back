package com.gdscssu.garbagecollector.domain.collection.service;

import com.gdscssu.garbagecollector.domain.collection.dto.CollectionDTO;
import com.gdscssu.garbagecollector.domain.collection.dto.CreateCollectionResponseDTO;
import com.gdscssu.garbagecollector.domain.collection.dto.GetCollectionResponseDTO;
import com.gdscssu.garbagecollector.domain.collection.dto.UpdateCollectionResponseDTO;
import com.gdscssu.garbagecollector.domain.collection.entity.Collection;
import com.gdscssu.garbagecollector.domain.collection.entity.CollectionType;
import com.gdscssu.garbagecollector.domain.collection.repository.CollectionRepository;
import com.gdscssu.garbagecollector.domain.user.entity.User;
import com.gdscssu.garbagecollector.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;

    public GetCollectionResponseDTO getCollection(Long id) {
        List<Collection> collections = collectionRepository.findAllByUserId(id);

        List<CollectionDTO> collectionDtos = collections.stream().map(
                collection -> CollectionDTO
                        .builder()
                        .type(collection.getType())
                        .level(collection.getLevel())
                        .exp(collection.getExp())
                        .build()
        ).collect(Collectors.toList());

        return GetCollectionResponseDTO
                .builder()
                .userId(id)
                .collections(collectionDtos)
                .build();
    }

    public CreateCollectionResponseDTO createCollection(Long userId, CollectionType type, Integer level, Integer exp) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));

        Collection collection = new Collection(user, type, level, exp);

        Collection saved = collectionRepository.save(collection);

        return CreateCollectionResponseDTO
                .builder()
                .collectionId(saved.getId())
                .build();
    }

    public UpdateCollectionResponseDTO updateCollection(Long collectionId, Integer level, Integer exp) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("해당하는 컬렉션을 찾을 수 없습니다."));

        collection.update(level, exp);
        Collection saved = collectionRepository.save(collection);

        return UpdateCollectionResponseDTO
                .builder()
                .type(saved.getType())
                .level(saved.getLevel())
                .exp(saved.getExp())
                .build();
    }
}
