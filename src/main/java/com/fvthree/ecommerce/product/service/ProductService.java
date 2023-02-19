package com.fvthree.ecommerce.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fvthree.ecommerce.product.model.Product;

import dto.CreateProductDTO;
import dto.ProductsPagedResponse;

@Service
public interface ProductService {
	Product create(CreateProductDTO productDTO);
	Product read(String slug);
	ProductsPagedResponse list(int pageNo, int pageSize, String sortBy, String sortDir);
	void remove(Long productId);
	Product update(Long productId, CreateProductDTO productDTO);
	Long productsCount();
	List<Product> productSearch(String keyword);
	List<Product> relatedProducts(Long productid, Long categoryid);
}
