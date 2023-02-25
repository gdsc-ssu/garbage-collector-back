package com.gdscssu.garbagecollector.domain.collection.repository;

import com.gdscssu.garbagecollector.domain.collection.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findAllByUserId(Long userId);


}
