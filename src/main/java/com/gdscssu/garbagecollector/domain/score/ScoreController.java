package com.gdscssu.garbagecollector.domain.score;

import com.gdscssu.garbagecollector.domain.score.dto.CreateScoreRequestDTO;
import com.gdscssu.garbagecollector.domain.score.dto.CreateScoreResponseDTO;
import com.gdscssu.garbagecollector.domain.score.dto.GetRankingResponseDTO;
import com.gdscssu.garbagecollector.domain.score.dto.GetScoreHistoryResponseDTO;
import com.gdscssu.garbagecollector.domain.score.service.ScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/score")
@Api(tags = "랭킹 API")
public class ScoreController {
    private final ScoreService scoreService;

    @PostMapping("/{userId}/{basketId}/{collectionId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "스코어 기록 생성", notes = "사용자가 쓰레기를 버림")
    public CreateScoreResponseDTO createScore(
            @PathVariable("userId") Long userId,
            @PathVariable("basketId") Long basketId,
            @PathVariable("collectionId") Long collectionId,
            @RequestBody CreateScoreRequestDTO createScoreRequestDTO
    ) {
        return scoreService.createScore(userId, basketId, collectionId, createScoreRequestDTO.getPoint());
    }

    @GetMapping("/ranking/total")
    @ApiOperation(value = "전체 랭킹 조회")
    public List<GetRankingResponseDTO> getTotalRanking() {
        return scoreService.getTotalRanking();
    }

    @GetMapping("/ranking/region")
    @ApiOperation(value = "지역 랭킹 조회")
    public List<GetRankingResponseDTO> getRegionRanking(@RequestParam String locationCode) {
        return scoreService.getRegionRanking(locationCode);
    }

    @GetMapping("/{userId}/history")
    @ApiOperation(value = "쓰레기 버린 기록 보기")
    public List<GetScoreHistoryResponseDTO> getScoreHistories(@PathVariable("userId") Long userId) {
        return scoreService.getScoreHistories(userId);
    }
}
