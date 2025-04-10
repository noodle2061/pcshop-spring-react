package com.id.akn.repository;

import com.id.akn.model.Gpu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GpuRepository extends JpaRepository<Gpu, Short> {
    @Query("SELECT g FROM Gpu g WHERE LOWER(g.model) = LOWER(:model)")
    Gpu findByNameNormalize(@Param("model") String model);
}
