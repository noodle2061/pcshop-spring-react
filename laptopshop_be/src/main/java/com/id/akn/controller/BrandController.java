package com.id.akn.controller;

import com.id.akn.exception.BrandException;
import com.id.akn.model.Brand;
import com.id.akn.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BrandController {
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Byte id) throws BrandException {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @PostMapping("/api/admin")
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) throws BrandException {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.createBrand(brand));
    }

    @PutMapping("/api/admin/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Byte id, @RequestBody Brand brand) throws BrandException {
        return ResponseEntity.ok(brandService.updateBrand(id, brand));
    }

    @DeleteMapping("/api/admin/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Byte id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
