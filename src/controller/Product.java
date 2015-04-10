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
	
	public String GetName(){
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
}
