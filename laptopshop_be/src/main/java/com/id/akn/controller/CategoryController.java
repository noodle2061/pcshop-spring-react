package com.id.akn.controller;

import com.id.akn.exception.CategoryException;
import com.id.akn.request.CategoryDTO;
import com.id.akn.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Byte id) throws CategoryException {
        return ResponseEntity.ok(categoryService.getCategoryDTOById(id));
    }

    @PostMapping("/api/admin")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) throws CategoryException {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDTO));
    }

    @PutMapping("/api/admin/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Byte id, @RequestBody CategoryDTO categoryDTO) throws CategoryException {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/api/admin/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Byte id) throws CategoryException {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
    }
}