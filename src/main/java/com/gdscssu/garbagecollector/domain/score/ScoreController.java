package com.gdscssu.garbagecollector.domain.score;

import com.gdscssu.garbagecollector.domain.score.dto.RankingResponseDTO;
import com.gdscssu.garbagecollector.domain.score.service.ScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
@Api(tags = "랭킹 API")
public class ScoreController {
    private final ScoreService scoreService;

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
