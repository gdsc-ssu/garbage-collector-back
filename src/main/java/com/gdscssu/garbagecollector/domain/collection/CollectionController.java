package com.gdscssu.garbagecollector.domain.collection;

import com.gdscssu.garbagecollector.domain.collection.dto.*;
import com.gdscssu.garbagecollector.domain.collection.service.CollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collection")
@Api(tags = "가비지 캐릭터 API")
public class CollectionController {
    private final CollectionService collectionService;

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "유저 컬렉션 생성", notes = "사용자가 첫 컬렉션을 생성함")
    public CreateCollectionResponseDTO createCollection(
            @PathVariable("userId") Long userId,
            @RequestBody CreateCollectionRequestDTO createCollectionRequestDTO
    ) {
        return collectionService.createCollection(
                userId,
                createCollectionRequestDTO.getType(),
                createCollectionRequestDTO.getLevel(),
                createCollectionRequestDTO.getExp()
        );
    }

    @PatchMapping("/{collectionId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "유저 컬렉션 업데이트", notes = "컬렉션 정보가 수정됨")
    public UpdateCollectionResponseDTO createCollection(
            @PathVariable("collectionId") Long collectionId,
            @RequestBody UpdateCollectionRequestDTO updateCollectionRequestDTO
    ) {
        return collectionService.updateCollection(
                collectionId,
                updateCollectionRequestDTO.getLevel(),
                updateCollectionRequestDTO.getExp()
        );
    }

    @GetMapping
    @ApiOperation(value = "유저 컬렉션 조회")
    public GetCollectionResponseDTO getCollection(@RequestParam Long userId) {
        return collectionService.getCollection(userId);
    }
}
