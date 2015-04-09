package controller;

public class Product {
	private int productID;
	private int sellerID;
	private String name;
	private String description;
	private String specs;
	private float price;
	private float shippingCost;
	private float rating;
	private int numInStock;
	
	Product(){
		productID = -1;
		sellerID = -1;
		name = "";
		description = "";
		specs = "";
		price = 0.0f;
		shippingCost = 0.0f;
		rating = 0.0f;
		numInStock = 0;
	}
	
	
}