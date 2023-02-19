package com.fvthree.ecommerce.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fvthree.ecommerce.product.model.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	boolean existsByName(String name);
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
	List<Product> findProductByNameAndDescriptionByKeyword(@Param("keyword") String keyword);
	
	@Query("SELECT p FROM Product p WHERE p.id = :prodId OR p.category = :catId")
	List<Product> findProductByProductIdAndCategoryId(@Param("prodId") Long prodId, @Param("catId") Long catId);
	
	Page<Product> findAll(Pageable pageable);
	
	Optional<Product> findBySlug(String slug);
}
