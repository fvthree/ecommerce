package com.fvthree.ecommerce.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fvthree.ecommerce.category.model.Category;
import com.fvthree.ecommerce.category.repository.CategoryRepository;
import com.fvthree.ecommerce.product.model.Product;
import com.fvthree.ecommerce.product.repository.ProductRepository;
import com.github.slugify.Slugify;

import dto.CreateProductDTO;
import dto.ProductsPagedResponse;
import exceptions.HTTP400Exception;
import exceptions.HTTP404Exception;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private final ProductRepository productRepository;
	
	@Autowired
	private final CategoryRepository categoryRepository;
	
	public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Product create(CreateProductDTO productDTO) {
		
		if (productRepository.existsByName(productDTO.getName())) {
			throw new HTTP400Exception("Product already exists");
		}
		
		String slugifiedName = new Slugify().slugify(productDTO.getName());
		
		Category category = categoryRepository.findById(productDTO.getCategory_id())
				.orElseThrow(() -> new HTTP404Exception("Category doesn't exist"));
		
		Product newProduct = Product.builder()
				.name(productDTO.getName())
				.description(productDTO.getDescription())
				.category(category)
				.photo(productDTO.getPhoto())
				.price(productDTO.getPrice())
				.quantity(productDTO.getQuantity())
				.shipping(productDTO.getShipping())
				.slug(slugifiedName)
				.sold(productDTO.getSold())
				.build();
		
		return productRepository.save(newProduct);
	}

	@Override
	public Product read(String slug) {
		return productRepository.findBySlug(slug)
				.orElseThrow(() -> new HTTP404Exception("Product not exists"));
	}

	@Override
	public ProductsPagedResponse list(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
        Page<Product> products = productRepository.findAll(pageable);
        
        List<Product> content = products.getContent();
        
        ProductsPagedResponse productResponse = ProductsPagedResponse.builder()
				.content(content)
				.pageNo(products.getNumber())
				.pageSize(products.getSize())
				.totalElements(products.getTotalElements())
				.totalPages(products.getTotalPages())
				.last(products.isLast())
				.build();
		
        return productResponse;
	}

	@Override
	public void remove(Long productId) {
		
		productRepository.findById(productId)
			.orElseThrow(() -> new HTTP404Exception("Product not found."));
		
		productRepository.deleteById(productId);
	}

	@Override
	public Product update(Long productId, CreateProductDTO productDTO) {
		
		Product productInDB = productRepository.findById(productId)
				.orElseThrow(() -> new HTTP404Exception("Product not found."));
		
		Category category = categoryRepository.findById(productDTO.getCategory_id())
				.orElseThrow(() -> new HTTP404Exception("Category not found."));
		
		String newSlug = new Slugify().slugify(productDTO.getName());
				
		productInDB.setName(productDTO.getName());
		productInDB.setDescription(productDTO.getDescription());
		productInDB.setSlug(newSlug);
		productInDB.setCategory(category);
		productInDB.setPhoto(productDTO.getPhoto());
		productInDB.setPrice(productDTO.getPrice());
		productInDB.setQuantity(productDTO.getQuantity());
		productInDB.setShipping(productDTO.getShipping());
		productInDB.setSold(0);
		
		return productRepository.save(productInDB);
	}

	@Override
	public Long productsCount() {
		return productRepository.count();
	}

	@Override
	public List<Product> productSearch(String keyword) {
		return productRepository.findProductByNameAndDescriptionByKeyword(keyword);
	}

	@Override
	public List<Product> relatedProducts(Long productid, Long categoryid) {
		return productRepository.findProductByProductIdAndCategoryId(productid, categoryid);
	}
}
