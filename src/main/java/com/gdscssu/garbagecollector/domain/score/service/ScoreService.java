package com.gdscssu.garbagecollector.domain.score.service;

import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import com.gdscssu.garbagecollector.domain.basket.repository.BasketRepository;
import com.gdscssu.garbagecollector.domain.collection.entity.Collection;
import com.gdscssu.garbagecollector.domain.collection.repository.CollectionRepository;
import com.gdscssu.garbagecollector.domain.score.dto.CreateScoreResponseDTO;
import com.gdscssu.garbagecollector.domain.score.dto.GetRankingResponseDTO;
import com.gdscssu.garbagecollector.domain.score.dto.GetScoreHistoryResponseDTO;
import com.gdscssu.garbagecollector.domain.score.entity.Score;
import com.gdscssu.garbagecollector.domain.score.repository.ScoreRepository;
import com.gdscssu.garbagecollector.domain.user.entity.User;
import com.gdscssu.garbagecollector.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final BasketRepository basketRepository;
    private final CollectionRepository collectionRepository;

    // TODO: pagination 적용하고 DTO 멤버에 rank 추가하기
    public List<GetRankingResponseDTO> getTotalRanking() {
        return scoreRepository.getRankings();
    }

    public List<GetRankingResponseDTO> getRegionRanking(String locationCode) {
        return scoreRepository.getRegionRankings(locationCode);
    }

    public CreateScoreResponseDTO createScore(Long userId, Long basketId, Long collectionId, Integer point) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));

        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new RuntimeException("해당하는 쓰레기통을 찾을 수 없습니다."));

        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("해당하는 컬렉션을 찾을 수 없습니다."));

        Score score = new Score(user, basket, collection, point);

        Score saved = scoreRepository.save(score);

        return CreateScoreResponseDTO
                .builder()
                .point(saved.getPoint())
                .build();
    }

    public List<GetScoreHistoryResponseDTO> getScoreHistories(Long userId) {
        List<Score> scores = scoreRepository.findAllByUserIdOrderByUpdatedAt(userId);

        return scores.stream()
                .map(score -> new GetScoreHistoryResponseDTO(
                        score.getCollection().getType(),
                        score.getPoint(),
                        score.getBasket().getId(),
                        score.getCreatedAt()
                    )
                )
                .collect(Collectors.toList());
    }
}
