package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;




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
	private int categoryID;
	private String picture;
	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public Product(){
		productID = -1;
		sellerID = -1;
		name = "";
		description = "";
		specs = "";
		price = 0.0f;
		//shippingCost = 0.0f;
		//rating = 0.0f;
		categoryID=0;
		numInStock = 0;
	}	
	

	public Product(int productID, 
			int sellerID, 
			String productName, 
			String description, 
			String specs, 
			float unitPrice, 
			int quantity, 
			int categoryID,String picture){
		this.productID = productID;
		this.sellerID = sellerID;
		this.name = productName;
		this.description = description;
		this.specs = specs;
		this.price = unitPrice;
		//shippingCost = 0.0f;
		//rating = 0.0f;
		this.numInStock = quantity;
		this.categoryID=categoryID;
		this.picture=picture;
	}	
	
	public int GetProductID(){
		return productID;
	}
	
	public void SetProductID(int x /*in, product ID*/){
		productID = x;
	}
	
	public int GetSellerID(){
		return sellerID;
	}
	
	public void SetSellerID(int x /*in, seller ID*/){
		sellerID = x;
	}
	
	public void SetProductName(String x /*in, seller ID*/){
		name = x;
	}
	
	public String GetProductName(){
		return name;
	}
	
	public void SetSellerID(String x /*in, product name*/){
		name = x;
	}
	
	public String GetDescription(){
		return description;
	}
	
	public void SetDescription(String x /*in, product description*/){
		description = x;
	}
	
	public String GetSpecs(){
		return specs;
	}
	
	public void SetSpecs(String x /*in, product specs*/){
		specs = x;
	}
	
	public float GetPrice(){
		return price;
	}
	
	public void SetPrice(float x /*in, product price*/){
		price = x;
	}
	
	public float GetShippingCost(){
		return shippingCost;
	}
	
	public void SetShippingCost(float x /*in, product shipping cost*/){
		shippingCost = x;
	}
	
	public float GetRating(){
		return rating;
	}
	
	public void SetRating(float x /*in, product rating*/){
		rating = x;
	}
	
	public int GetNumInStock(){
		return numInStock;
	}
	
	public void SetNumInStock(int x /*in, number of products in stock*/){
		numInStock = x;
	}
	
	public int GetCategoryID(){
		return categoryID;
	}
	
	public void SetCategoryID(int x /*in, category*/){
		categoryID = x;
	}
	
	
}

