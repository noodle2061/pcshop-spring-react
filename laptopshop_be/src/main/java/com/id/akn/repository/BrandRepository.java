package com.id.akn.repository;

import com.id.akn.model.Brand;
import com.id.akn.model.Cart;
import com.id.akn.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Byte> {
    @Query("SELECT b FROM Brand b WHERE LOWER(b.name) = LOWER(:name)")
    Brand findByNameNormalize(@Param("name") String name);
}
