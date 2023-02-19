package com.fvthree.ecommerce.category.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fvthree.ecommerce.category.model.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>{
	Optional<Category> findBySlug(String slug);
	boolean existsByName(String name);
	boolean existsBySlug(String slug);
}
