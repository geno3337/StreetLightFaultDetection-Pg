package com.example.StreetLightFaultDetection.repository;

import com.example.StreetLightFaultDetection.Entity.LightDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LightDetailRepository extends JpaRepository<LightDetail,Integer> {

    @Transactional
    @Query("SELECT l FROM LightDetail l WHERE CONCAT(l.id, l.place) LIKE %?1%")
    Page<LightDetail> search(String key, Pageable pageRequest);

    @Transactional
    @Query("SELECT l FROM LightDetail l WHERE l.fault=true" )
    Page<LightDetail> getFaultedLightByfault(Pageable pageRequest);
}
