package com.advertisementboard.rest.api;

import com.advertisementboard.data.dto.category.CategoryDto;
import com.advertisementboard.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok()
                .body(categoryService.getCategories());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") long id){
        return ResponseEntity.ok()
                .body(categoryService.getCategory(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCategory(@RequestBody CategoryDto category){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(category));
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDto category){
        categoryService.updateCategory(category);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable("id") long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

}
