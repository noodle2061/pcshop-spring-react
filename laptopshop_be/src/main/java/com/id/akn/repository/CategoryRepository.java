package com.id.akn.repository;

import com.id.akn.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import com.id.akn.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Byte> {
    @Query("SELECT c FROM Category c WHERE LOWER(c.name) = LOWER(:name)")
    Category findByNameNormalize(@Param("name") String name);
}
