package com.iss.inventorymanagement.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iss.inventorymanagement.excel.ExcelGenerator;
import com.iss.inventorymanagement.exception.ProductException;
import com.iss.inventorymanagement.exception.UserException;
import com.iss.inventorymanagement.jwt.JwtUtil;
import com.iss.inventorymanagement.model.request.ProductRequestModel;
import com.iss.inventorymanagement.model.response.ProductResponseModel;
import com.iss.inventorymanagement.repository.implementation.ProductRepositoryImplementation;

/*
 * This Class Controls the endpoints for getting the products
*/

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	ProductRepositoryImplementation productRepository;
	
	@Autowired
	JwtUtil jwtUtil;
	
	// Function For getting if jwt is for admin  
	private void adminAccess(String jwt) {
		//if the user doesnot have the admin access then throw error
		if (!jwtUtil.isAdmin(jwt.substring(7))) {
			throw new UserException("Unauthorized action performed. User should be admin");
		}
	}
	
	@GetMapping
	public ResponseEntity<List<ProductResponseModel>> getProducts() {
		/*
		 * Getting all the products and returning the list of the Product response model
		 * Does not requires authentication
		*/
		List<ProductResponseModel> productList = productRepository.findAllProducts();
		return new ResponseEntity<>(productList, HttpStatus.OK);
	}

	@GetMapping(path = "{productId}")
	public ResponseEntity<ProductResponseModel> getProductById(@PathVariable Long productId) {
		//endpoint to get the specific product
		ProductResponseModel productResponseModel = productRepository.findProductById(productId);
		if (productResponseModel != null) {
			return new ResponseEntity<>(productResponseModel, HttpStatus.OK);
		} else {
			throw new ProductException("Product Not found");
		}
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, 
							MediaType.APPLICATION_JSON_VALUE }, 
				produces = { MediaType.APPLICATION_XML_VALUE, 
						MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> insertProduct(@Valid @RequestBody ProductRequestModel newProduct,@RequestHeader("Authorization") String jwt) {
		/*
		 * Function to get insert the product into the database
		 * This Requires admin previliges.
		*/
		adminAccess(jwt);
		
		try {
			int rowAffected = productRepository.addProduct(newProduct);
			return new ResponseEntity<>(rowAffected + " product was added successfully.", HttpStatus.CREATED);
		} catch (Exception e) {
			//return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
			throw new ProductException(e.toString());
		}
	}
	
	@PutMapping(path = "{productId}",
				consumes = { 
						MediaType.APPLICATION_XML_VALUE, 
						MediaType.APPLICATION_JSON_VALUE }, 
				produces = { 
						MediaType.APPLICATION_XML_VALUE, 
						MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductRequestModel product,@RequestHeader("Authorization") String jwt){
		/*
		 * Function to get update the product into the database. The endpoint contains the productid for which update is to be done
		 * This Requires admin previliges.
		*/
		adminAccess(jwt);
		try {
			int rowAffected = productRepository.updateProduct(productId, product);
			return new ResponseEntity<>(rowAffected + " product was updated successfully.", HttpStatus.OK);
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new ProductException("Product Does Not Exists");
		}
	}
	

	@DeleteMapping(path = "{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long productId,@RequestHeader("Authorization") String jwt) {
		/*
		 * Function to delete the product with the product id passed in the endpoint
		 * Requires admin previliges.
		*/
		adminAccess(jwt);
		try {
			int rowAffected = productRepository.deleteProductById(productId);
			return new ResponseEntity<>(
					rowAffected + " product was deleted successfully. Product Id was:" + productId,
					HttpStatus.OK);
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new ProductException("Product Does Not Exists");
		}
	}
	
	@GetMapping(path = "/export-to-excel")
	public void getProductExcel(HttpServletResponse response) throws IOException  {
		/*
		 * Endpoint to export the product in the database to excel
		 * Handles the IOException
		*/
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
		List<ProductResponseModel> productList = productRepository.findAllProducts();
		ExcelGenerator generator = new ExcelGenerator(productList);
        generator.generateExcelFile(response);
	}
}
