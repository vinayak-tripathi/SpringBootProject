package com.iss.inventorymanagement.model.request;

import javax.validation.constraints.NotNull;

public class ProductRequestModel {
	@NotNull(message = "Name cannot be null")
	private String name;
	@NotNull(message = "Price cannot be null")
	private float price;
	@NotNull(message = "Category cannot be null")
	private String category;
	@NotNull(message = "Rating cannot be null")
	private float rating;
	@NotNull(message = "Quantity cannot be null")
	private int quantity;
	@NotNull(message = "Description cannot be null")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("vt"+name);
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		System.out.println(category);
		this.category = category;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		System.out.println(rating);
		this.rating = rating;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		System.out.println(quantity);
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		System.out.println(description);
		this.description = description;
	}

}
