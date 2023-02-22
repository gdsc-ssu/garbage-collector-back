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

    @Query(value = "select distinct * from Basket as b where b.lat>=:lat1 and b.lat<=:lat2 and b.lng>=:lng1 " +
            "and b.lng<=:lng2 ",nativeQuery = true)
    List<Basket>findBasketByLngAndLat(@Param("lng1")Double lng1, @Param("lat1")Double lat1,
                                      @Param("lng2")Double lng2,@Param("lat2")Double lat2);

    Optional<Basket> findBasketById(Long id);
}
