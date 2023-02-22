package com.gdscssu.garbagecollector.domain.basket.repository;

import com.gdscssu.garbagecollector.domain.basket.entity.UserBasketMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserBasketRepository extends JpaRepository<UserBasketMapping,Long> {

    @Query("select count(*) from UserBasketMapping ub where ub.basket.id=:basketId and ub.user.id=:userId")
    int UserBasketCount(@Param("userId")Long userId, @Param("basketId")Long basketId);
}
