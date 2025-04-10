package com.id.akn.serviceimpl;

import com.id.akn.exception.CategoryException;
import com.id.akn.exception.CpuTechException;
import com.id.akn.model.Category;
import com.id.akn.model.CpuTech;
import com.id.akn.repository.CategoryRepository;
import com.id.akn.request.CategoryDTO;
import com.id.akn.service.CategoryService;
import com.id.akn.util.NormalizeString;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryDTOById(Byte id) throws CategoryException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException("Category not found"));
        return convertToDTO(category);
    }

    @Override
    public Category getCategoryById(Byte id) throws CategoryException {
       return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException("Category not found"));
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws CategoryException {
        Category category = new Category();
        String normalizedCategoryName = NormalizeString.normalize(categoryDTO.getName());
        if (categoryRepository.findByNameNormalize(normalizedCategoryName) != null) {
            throw new IllegalArgumentException("Category with the name '" + categoryDTO.getName() + "' already exists.");
        }
        category.setName(categoryDTO.getName());
        return convertToDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO updateCategory(Byte id, CategoryDTO categoryDTO) throws CategoryException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException("Category not found"));
        String normalizedCategoryName = NormalizeString.normalize(categoryDTO.getName());
        Category existingCategoryName = categoryRepository.findByNameNormalize(normalizedCategoryName);
        if (existingCategoryName != null && !existingCategoryName.getId().equals(id)) {
            throw new CategoryException("Category with the name '" + categoryDTO.getName() + "' already exists.");
        }
        category.setName(categoryDTO.getName());
        return convertToDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Byte id) throws CategoryException {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
    @Override
    public CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}

