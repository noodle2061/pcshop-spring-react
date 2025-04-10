package com.id.akn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.id.akn.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	@Query("SELECT r FROM Review r WHERE r.laptop.id=:laptopId")
	public List<Review> getAllLaptopReviews(@Param("laptopId") Long laptopId);
}
