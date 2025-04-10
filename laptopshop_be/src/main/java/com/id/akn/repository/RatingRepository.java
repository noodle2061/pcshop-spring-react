package com.id.akn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.id.akn.model.Rating;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
	@Query("SELECT r FROM Rating r WHERE r.laptop.id=:laptopId")
	public List<Rating> getAllLaptopRatings(@Param("laptopId") Long laptopId);
}
