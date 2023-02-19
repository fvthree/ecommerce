package com.fvthree.ecommerce.category.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fvthree.ecommerce.category.model.Category;
import com.fvthree.ecommerce.category.service.CategoryService;

import dto.CategoriesPagedResponse;
import dto.CreateCategoryDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class CategoryController extends CategoryAbstractController {

	@Autowired
	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping("/category")
	public ResponseEntity<?> create(@Valid @RequestBody CreateCategoryDTO categoryDto) {
		Category category = categoryService.create(categoryDto);
        return ResponseEntity.ok().body("New Category has been saved with ID:" + category.getId());
	}
	
    @GetMapping("/categories")
    public CategoriesPagedResponse getAllCategories(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "dateCreated", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        log.info(" size ::: " + pageSize + ", number ::: " + pageNo + ", sortBy ::: " + sortBy + ", sortDir ::: " + sortDir);
        return categoryService.list(pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/category/{slug}")
    public ResponseEntity<?> getCategoryBySlug(@PathVariable(name = "slug") String slug) {
        return ResponseEntity.ok(categoryService.read(slug));
    }
    
    @PutMapping("/category/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody CreateCategoryDTO categoryDto, 
    		@PathVariable(name = "id") Long id) {
       return new ResponseEntity<>(categoryService.update(id, categoryDto), HttpStatus.OK);
    }
    
    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
        categoryService.remove(id);
        return new ResponseEntity<>("Category deleted successfully.", HttpStatus.OK);
    }
}
