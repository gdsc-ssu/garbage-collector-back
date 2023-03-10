package com.gdscssu.garbagecollector.domain.basket.repository;

import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import com.gdscssu.garbagecollector.domain.basket.entity.BasketType;
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

    @Query(value = "select  * from Basket as b where b.lat>=:lat1 and b.lat<=:lat2 and b.lng>=:lng1 " +
            "and b.lng<=:lng2 ",nativeQuery = true)
    List<Basket>findBasketByLngAndLat(@Param("lng1")Double lng1, @Param("lat1")Double lat1,
                                      @Param("lng2")Double lng2,@Param("lat2")Double lat2);

    Optional<Basket> findBasketById(Long id);

    @Query(value = "select  * from Basket as b where ROUND(b.lng,2) like ROUND(:lng,2) and ROUND(b.lat,2) like ROUND(:lat,2) and b.type like :type",nativeQuery = true)
    List<Basket>basketRecommendList(@Param("lng")Double lng, @Param("lat")Double lat,@Param("type")String type);
}
