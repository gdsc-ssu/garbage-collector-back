package com.gdscssu.garbagecollector.domain.score.service;

import com.gdscssu.garbagecollector.domain.score.dto.RankingResponseDTO;
import com.gdscssu.garbagecollector.domain.score.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;

    // TODO: pagination 적용하고 DTO 멤버에 rank 추가하기
    public List<RankingResponseDTO> getTotalRanking() {
        return scoreRepository.getRankings();
    }

    public List<RankingResponseDTO> getRegionRanking(String locationCode) {
        return scoreRepository.getRegionRankings(locationCode);
    }
}
