package com.gdscssu.garbagecollector.domain.basket.repository;

import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface BasketRepository extends JpaRepository<Basket,Long> {

    @Query(value = "select distinct * from Basket as b where left(b.lng,8) like left(:lng,8) and left(b.lat,7) like left(:lat,7) ",nativeQuery = true)
    List<Basket>findBasketByLngAndLat(@Param("lng")String lng, @Param("lat")String lat);
}
