package com.id.akn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.id.akn.model.Laptop;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Integer>, JpaSpecificationExecutor<Laptop> {

//    @Query("SELECT l FROM Laptop l " +
//            "JOIN l.categories c " +
//            "WHERE (:category IS NULL OR c.name = :category) " +
//            "AND ((:minPrice IS NULL AND :maxPrice IS NULL) " +
//            "OR (l.price * (1 - l.discountPercent / 100) BETWEEN :minPrice AND :maxPrice)) " +
//            "AND (:minDiscount IS NULL OR l.discountPercent >= :minDiscount) " +
//            "ORDER BY " +
//            "CASE WHEN :sort = 'price_low' THEN (l.price * (1 - l.discountPercent / 100)) END ASC, " +
//            "CASE WHEN :sort = 'price_high' THEN (l.price * (1 - l.discountPercent / 100)) END DESC")
//    List<Laptop> filterLaptops(@Param("category") String category,
//                                      @Param("minPrice") Long minPrice,
//                                      @Param("maxPrice") Long maxPrice,
//                                      @Param("minDiscount") Float minDiscount,
//                                      @Param("sort") String sort);

    @Query("SELECT l FROM Laptop l WHERE LOWER(l.model) LIKE LOWER(CONCAT('%', :model, '%'))")
    List<Laptop> searchLaptop(@Param("model") String model);

    @Query("SELECT l FROM Laptop l ORDER BY l.discountPercent DESC")
    List<Laptop> findTop10ByHighestDiscount();

    @Query(value = "SELECT COUNT(*) FROM laptop", nativeQuery = true)
    Long getTotalProduct();
    boolean existsLaptopByModel(String model);
}
