package com.fvthree.ecommerce.category.service;

import com.fvthree.ecommerce.category.model.Category;

import dto.CategoriesPagedResponse;
import dto.CreateCategoryDTO;

public interface CategoryService {
	Category create(CreateCategoryDTO categoryDTO);
	Category update(Long id, CreateCategoryDTO categoryDTO);
	void remove(Long id);
	Category read(String slug);
	CategoriesPagedResponse list(int pageNo, int pageSize, String sortBy, String sortDir);
}
