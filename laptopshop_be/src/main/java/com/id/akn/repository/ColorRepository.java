package com.id.akn.repository;

import com.id.akn.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Byte> {
    @Query("SELECT c FROM Color c WHERE LOWER(c.name) = LOWER(:name)")
    Color findByNameNormalize(@Param("name") String name);
}