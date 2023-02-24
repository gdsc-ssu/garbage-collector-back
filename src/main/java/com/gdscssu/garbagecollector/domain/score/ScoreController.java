package com.gdscssu.garbagecollector.domain.score;

import com.gdscssu.garbagecollector.domain.score.dto.CreateScoreRequestDTO;
import com.gdscssu.garbagecollector.domain.score.dto.CreateScoreResponseDTO;
import com.gdscssu.garbagecollector.domain.score.dto.RankingResponseDTO;
import com.gdscssu.garbagecollector.domain.score.service.ScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
@Api(tags = "랭킹 API")
public class ScoreController {
    private final ScoreService scoreService;

    @PostMapping("/{userId}/{basketId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "스코어 기록 생성", notes = "사용자가 쓰레기를 버림")
    public CreateScoreResponseDTO createScore(
            @PathVariable("userId") Long userId,
            @PathVariable("basketId") Long basketId,
            @RequestBody CreateScoreRequestDTO createScoreRequestDTO
    ) {
        return scoreService.createScore(userId, basketId, createScoreRequestDTO.getPoint());
    }

    @GetMapping("/total")
    @ApiOperation(value = "전체 랭킹 조회")
    public List<RankingResponseDTO> getTotalRanking() {
        return scoreService.getTotalRanking();
    }

    @GetMapping("/region")
    @ApiOperation(value = "전체 랭킹 조회")
    public List<RankingResponseDTO> getRegionRanking(@RequestParam String locationCode) {
        return scoreService.getRegionRanking(locationCode);
    }
}
