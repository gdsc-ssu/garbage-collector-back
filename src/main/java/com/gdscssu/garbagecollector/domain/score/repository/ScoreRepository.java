package com.gdscssu.garbagecollector.domain.score.repository;

import com.gdscssu.garbagecollector.domain.score.dto.GetRankingResponseDTO;
import com.gdscssu.garbagecollector.domain.score.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ScoreRepository extends JpaRepository<Score, Long> {
    @Query("SELECT new com.gdscssu.garbagecollector.domain.score.dto.GetRankingResponseDTO(s.user.nickname, SUM(s.point)) " +
            "FROM Score s " +
            "GROUP BY s.user " +
            "ORDER BY SUM(s.point) DESC")
    List<GetRankingResponseDTO> getRankings();


    @Query("SELECT new com.gdscssu.garbagecollector.domain.score.dto.GetRankingResponseDTO(s.user.nickname, SUM(s.point)) " +
            "FROM Score s " +
            "WHERE s.basket.location.code = :locationCode " +
            "GROUP BY s.user " +
            "ORDER BY SUM(s.point) DESC")
    List<GetRankingResponseDTO> getRegionRankings(@Param("locationCode") String locationCode);

    List<Score> findAllByUserIdOrderByUpdatedAt(@Param("userId") Long userId);

    Integer countByUserIdAndBasketId(@Param("userId") Long userId, @Param("basketId") Long basketId);
}
