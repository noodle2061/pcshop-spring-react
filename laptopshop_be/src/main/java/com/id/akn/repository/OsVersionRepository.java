package com.id.akn.repository;

import com.id.akn.model.Color;
import com.id.akn.model.OsVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OsVersionRepository extends JpaRepository<OsVersion, Short> {
    @Query("SELECT ov FROM OsVersion ov WHERE LOWER(ov.version) = LOWER(:version)")
    OsVersion findByNameNormalize(@Param("version") String version);
}
