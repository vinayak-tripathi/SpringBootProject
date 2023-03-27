package com.iss.inventorymanagement.repository.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.iss.inventorymanagement.model.request.ProductRequestModel;
import com.iss.inventorymanagement.model.response.ProductResponseModel;
import com.iss.inventorymanagement.repository.ProductRepository;

@Repository
public class ProductRepositoryImplementation implements ProductRepository {

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public int addProduct(ProductRequestModel product) {
		return jdbc.update(
				"INSERT INTO dbo.[products] (dbo.[products].[name], dbo.[products].[price], dbo.[products].[category], dbo.[products].[rating], dbo.[products].[quantity], dbo.[products].[description]) VALUES (?, ?, ?, ?, ?, ?)",
				new Object[] { product.getName(), product.getPrice(), product.getCategory(), product.getRating(),
						product.getQuantity(), product.getDescription() });
	}

	@Override
	public int updateProduct(Long productId,ProductRequestModel product) {
			jdbc.queryForObject("SELECT * from products where id=?",
					BeanPropertyRowMapper.newInstance(ProductResponseModel.class), productId);
			
			return jdbc.update(
					"Update dbo.[products] Set dbo.[products].[name] = ?, dbo.[products].[price]= ?, dbo.[products].[category] = ?, dbo.[products].[rating] = ?, dbo.[products].[quantity] = ?, dbo.[products].[description] = ? where id = ?",
					new Object[] { product.getName(), product.getPrice(), product.getCategory(), product.getRating(),
							product.getQuantity(), product.getDescription(), productId });
		
	}

	@Override
	public int deleteProductById(Long id) {
		jdbc.queryForObject("SELECT * from products where id=?",
				BeanPropertyRowMapper.newInstance(ProductResponseModel.class), id);
		
		return jdbc.update("DELETE FROM products WHERE id=?", id);
	}

	@Override
	public List<ProductResponseModel> findAllProducts() {
		return jdbc.query("SELECT * from products", BeanPropertyRowMapper.newInstance(ProductResponseModel.class));
	}

	@Override
	public List<ProductResponseModel> findProductByCategory(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductResponseModel findProductById(Long productId) {
		try {
			ProductResponseModel product = jdbc.queryForObject("SELECT * from products where id=?",
					BeanPropertyRowMapper.newInstance(ProductResponseModel.class), productId);

			return product;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	@Override
	public int deleteAll() {
		return jdbc.update("DELETE FROM dbo.[products]");
	}

}
