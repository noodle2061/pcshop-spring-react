package com.id.akn.repository;

import com.id.akn.model.Brand;
import com.id.akn.model.CpuTech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuTechRepository extends JpaRepository<CpuTech, Short> {
    @Query("SELECT ct FROM CpuTech ct WHERE LOWER(ct.name) = LOWER(:name)")
    CpuTech findByNameNormalize(@Param("name") String name);
}

