package com.gdscssu.garbagecollector.domain.collection;

import com.gdscssu.garbagecollector.domain.collection.dto.CollectionResponseDTO;
import com.gdscssu.garbagecollector.domain.collection.service.CollectionService;
import com.gdscssu.garbagecollector.domain.score.dto.RankingResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collection")
@Api(tags = "가비지 캐릭터 API")
public class CollectionController {
    private final CollectionService collectionService;

    @GetMapping
    @ApiOperation(value = "유저 컬렉션 조회")
    public CollectionResponseDTO getCollection(@RequestParam Long userId) {
        return collectionService.getCollection(userId);
    }
}
