package com.iss.inventorymanagement.repository;

import java.util.List;

import com.iss.inventorymanagement.model.request.ProductRequestModel;
import com.iss.inventorymanagement.model.response.ProductResponseModel;

public interface ProductRepository {
	int addProduct(ProductRequestModel product);

	int updateProduct(Long productId,ProductRequestModel product);

	int deleteProductById(Long id);

	List<ProductResponseModel> findAllProducts();
	
	List<ProductResponseModel> findProductByCategory(String title);

	ProductResponseModel findProductById(Long productId);

	int deleteAll();
}
