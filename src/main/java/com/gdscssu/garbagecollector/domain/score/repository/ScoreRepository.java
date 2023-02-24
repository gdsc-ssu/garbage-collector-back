package com.gdscssu.garbagecollector.domain.score.repository;

import com.gdscssu.garbagecollector.domain.score.dto.RankingResponseDTO;
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
    @Query("SELECT new com.gdscssu.garbagecollector.domain.score.dto.RankingResponseDTO(s.user.nickname, SUM(s.score)) " +
            "FROM Score s " +
            "GROUP BY s.user " +
            "ORDER BY SUM(s.score) DESC")
    List<RankingResponseDTO> getRankings();


    @Query("SELECT new com.gdscssu.garbagecollector.domain.score.dto.RankingResponseDTO(s.user.nickname, SUM(s.score)) " +
            "FROM Score s " +
            "WHERE s.basket.location.code = :locationCode " +
            "GROUP BY s.user " +
            "ORDER BY SUM(s.score) DESC")
    List<RankingResponseDTO> getRegionRankings(@Param("locationCode") String locationCode);
}
