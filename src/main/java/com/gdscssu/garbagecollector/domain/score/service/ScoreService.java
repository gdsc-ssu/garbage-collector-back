package com.gdscssu.garbagecollector.domain.score.service;

import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import com.gdscssu.garbagecollector.domain.basket.repository.BasketRepository;
import com.gdscssu.garbagecollector.domain.score.dto.CreateScoreResponseDTO;
import com.gdscssu.garbagecollector.domain.score.dto.RankingResponseDTO;
import com.gdscssu.garbagecollector.domain.score.entity.Score;
import com.gdscssu.garbagecollector.domain.score.repository.ScoreRepository;
import com.gdscssu.garbagecollector.domain.user.entity.User;
import com.gdscssu.garbagecollector.domain.user.repository.UserRepository;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final BasketRepository basketRepository;

    // TODO: pagination 적용하고 DTO 멤버에 rank 추가하기
    public List<RankingResponseDTO> getTotalRanking() {
        return scoreRepository.getRankings();
    }

    public List<RankingResponseDTO> getRegionRanking(String locationCode) {
        return scoreRepository.getRegionRankings(locationCode);
    }

    public CreateScoreResponseDTO createScore(Long userId, Long basketId, Integer point) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));

        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new RuntimeException("해당하는 쓰레기통을 찾을 수 없습니다."));

        Score score = new Score(user, basket, point);

        Score saved = scoreRepository.save(score);

        return CreateScoreResponseDTO
                .builder()
                .point(saved.getPoint())
                .build();
    }
}
