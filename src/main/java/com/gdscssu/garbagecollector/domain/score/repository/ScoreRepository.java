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
    @Query("SELECT new com.gdscssu.garbagecollector.domain.score.dto.GetRankingResponseDTO(s.user.id,s.user.nickname, s.user.profileImg,SUM(s.point)) " +
            "FROM Score s  join User as u on s.user.id=u.id "+
            "GROUP BY u.id " +
            "ORDER BY SUM(s.point) DESC , u.nickname "
            )
    List<GetRankingResponseDTO> getRankings();

    @Query("SELECT new com.gdscssu.garbagecollector.domain.score.dto.GetRankingResponseDTO(s.user.id,s.user.nickname,s.user.profileImg, SUM(s.point))  from Score s order by sum(s.point) desc")
    List<GetRankingResponseDTO> getRankingsV2();


    @Query("SELECT new com.gdscssu.garbagecollector.domain.score.dto.GetRankingResponseDTO(s.user.id,s.user.nickname,s.user.profileImg, SUM(s.point)) " +
            "FROM Score s " +
            "WHERE s.basket.location.code = :locationCode " +
            "GROUP BY s.user " +
            "ORDER BY SUM(s.point) DESC")
    List<GetRankingResponseDTO> getRegionRankings(@Param("locationCode") String locationCode);

    List<Score> findAllByUserIdOrderByUpdatedAt(@Param("userId") Long userId);
}
