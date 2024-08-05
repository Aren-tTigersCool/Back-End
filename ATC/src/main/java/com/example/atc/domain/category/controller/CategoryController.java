package com.example.atc.domain.category.controller;
import com.example.atc.domain.category.dto.CategoryDTO;
import com.example.atc.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        return categoryService.getAllCategory();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}