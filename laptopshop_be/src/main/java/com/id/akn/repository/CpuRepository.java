package com.id.akn.repository;

import com.id.akn.model.Cpu;
import com.id.akn.model.CpuTech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuRepository extends JpaRepository<Cpu, Short> {
    @Query("SELECT c FROM Cpu c WHERE LOWER(c.model) = LOWER(:model)")
    Cpu findByNameNormalize(@Param("model") String model);
    Cpu findByModelAndTechnology(String model, CpuTech technology);
}
