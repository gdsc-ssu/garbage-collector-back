package com.gdscssu.garbagecollector.domain.basket.repository;

import com.gdscssu.garbagecollector.domain.basket.entity.UserBasketMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserBasketRepository extends JpaRepository<UserBasketMapping,Long> {
}
