package com.gdscssu.garbagecollector.domain.trash.repository;

import com.gdscssu.garbagecollector.domain.trash.entity.Trash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrashRepository extends JpaRepository<Trash,Long> {

    @Query("select count(type2) from Trash where type2='PLASTIC' and user.id=:userId")
    int findPlasticCount(@Param("userId")Long userId);

    @Query("select count(type2) from Trash where type2='PAPER' and user.id=:userId")
    int findPaperCount(@Param("userId")Long userId);

    @Query("select count(type2) from Trash where type2='CAN' and user.id=:userId")
    int findCanCount(@Param("userId")Long userId);

    @Query("select count(type2) from Trash where type2='GLASS' and user.id=:userId")
    int findGlassCount(@Param("userId")Long userId);

    @Query("select count(type2) from Trash where type2='GENERAL' and user.id=:userId")
    int findGeneralCount(@Param("userId")Long userId);

}
