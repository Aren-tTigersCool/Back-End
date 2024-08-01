package com.example.atc.domain.category.service;
import com.example.atc.domain.category.dto.CategoryDTO;
import com.example.atc.domain.category.entity.Category;
import com.example.atc.domain.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public ResponseEntity<?> createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(category);
        return ResponseEntity.ok(new CategoryDTO(category.getCategoryId(), category.getCategoryName()));
    }
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(cat -> new CategoryDTO(cat.getCategoryId(), cat.getCategoryName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }
    public ResponseEntity<?> getCategoryById(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            return ResponseEntity.ok(new CategoryDTO(category.getCategoryId(), category.getCategoryName()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<?> updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            category.setCategoryName(categoryDTO.getCategoryName());
            categoryRepository.save(category);
            return ResponseEntity.ok(new CategoryDTO(category.getCategoryId(), category.getCategoryName()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<?> deleteCategory(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}