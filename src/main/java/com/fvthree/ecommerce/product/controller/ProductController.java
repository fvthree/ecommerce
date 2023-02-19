package com.fvthree.ecommerce.product.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fvthree.ecommerce.product.model.Product;
import com.fvthree.ecommerce.product.service.ProductService;

import dto.CreateProductDTO;
import dto.ProductsPagedResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class ProductController extends ProductAbstractController {
	
	@Autowired
	private ProductService productService;

	@PostMapping("/product")
	public ResponseEntity<?> create(@RequestBody @Valid CreateProductDTO dto) {
		Product savedProduct = productService.create(dto);
		return ResponseEntity.ok().body("Created a product with ID: " + savedProduct.getId());
	}
	
	@GetMapping("/products")
    public ProductsPagedResponse getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "dateCreated", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        log.info(" size ::: " + pageSize + ", number ::: " + pageNo + ", sortBy ::: " + sortBy + ", sortDir ::: " + sortDir);
        return productService.list(pageNo, pageSize, sortBy, sortDir);
    }
	
	@GetMapping("/product/{slug}")
	public ResponseEntity<?> getProductBySlug(@PathVariable(name="slug") String slug) {
		return ResponseEntity.ok().body(productService.read(slug));
	}
	
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable(name="productId") Long productId) {
		productService.remove(productId);
		return ResponseEntity.ok().body("Removed a product with id : " + productId);
	}
	
	@PutMapping("/product/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable(name="productId") Long id,
			@RequestBody @Valid CreateProductDTO dto) {
		return ResponseEntity.ok().body(productService.update(id, dto));
	}
	
	@GetMapping("/products-count")
	public ResponseEntity<?> productCount() {
		Long count = productService.productsCount();
		return ResponseEntity.ok().body(count);
	}
	
	@GetMapping("/products/search/{keyword}")
	public ResponseEntity<?> productSearch(@PathVariable(name="keyword") String keyword) {
		return ResponseEntity.ok().body(productService.productSearch(keyword));
	}
	
	@GetMapping("/related-products/{productId}/{categoryId}")
	public ResponseEntity<?> RelatedProductSearch(@PathVariable(name="productId") Long productId,
			@PathVariable(name="categoryId") Long categoryId) {
		return ResponseEntity.ok().body(productService.relatedProducts(productId, categoryId));
	}
	

}
