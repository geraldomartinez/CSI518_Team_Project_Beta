package controller;
import java.util.Base64;

public class Product {
	private int productID;
	private int sellerID;
	private String name;
	private String description;
	private String specs;
	private float price;
	private float groundCost;
	private float twoCost;
	private float nextCost;
	private int rating;
	private int reviewCount;
	private int numInStock;
	private int categoryID;
	private byte[] pictureBlob;


	public Product(){
		this.productID = -1;
		this.sellerID = -1;
		this.name = "";
		this.description = "";
		this.specs = "";
		this.price = 0.0f;
		this.groundCost = -1.0f;
		this.twoCost = -1.0f;
		this.nextCost = -1.0f;
		//this.rating = 0.0f;
		this.categoryID=0;
		this.numInStock = 0;
		this.pictureBlob=null;
		this.setReviewCount(0);
	}	
	

	public Product(int productID, 
			int sellerID, 
			String productName, 
			String description, 
			String specs, 
			float unitPrice, 
			int quantity, 
			int categoryID,
			byte[] pictureBlob, 
			int rating, 
			int reviewCount,
			float groundCost,
			float twoCost,
			float nextCost){
		this.productID = productID;
		this.sellerID = sellerID;
		this.name = productName;
		this.description = description;
		this.specs = specs;
		this.price = unitPrice;
		this.groundCost = groundCost;
		this.twoCost = twoCost;
		this.nextCost = nextCost;
		this.rating = rating;
		this.setReviewCount(reviewCount);
		this.numInStock = quantity;
		this.categoryID=categoryID;
		this.pictureBlob=pictureBlob;
	}	
	
	public int GetProductID(){
		return this.productID;
	}
	
	public void SetProductID(int x /*in, product ID*/){
		this.productID = x;
	}
	
	public int GetSellerID(){
		return this.sellerID;
	}
	
	public void SetSellerID(int x /*in, seller ID*/){
		this.sellerID = x;
	}
	
	public void SetProductName(String x /*in, seller ID*/){
		this.name = x;
	}
	
	public String GetProductName(){
		return this.name;
	}
	
	public void SetSellerID(String x /*in, product name*/){
		this.name = x;
	}
	
	public String GetDescription(){
		return this.description;
	}
	
	public void SetDescription(String x /*in, product description*/){
		this.description = x;
	}
	
	public String GetSpecs(){
		return this.specs;
	}
	
	public void SetSpecs(String x /*in, product specs*/){
		this.specs = x;
	}
	
	public float GetPrice(){
		return this.price;
	}
	
	public void SetPrice(float x /*in, product price*/){
		this.price = x;
	}
	
	public float GetGroundShippingCost(){
		return this.groundCost;
	}
	
	public void SetGroundShippingCost(float x /*in, ground shipping cost*/){
		this.groundCost = x;
	}
	
	public float GetTwoDayShippingCost(){
		return this.twoCost;
	}
	
	public void SetTwoDayShippingCost(float x /*in, two-day shipping cost*/){
		this.twoCost = x;
	}
	
	public float GetNextDayShippingCost(){
		return this.nextCost;
	}
	
	public void SetNextDayShippingCost(float x /*in, next-day shipping cost*/){
		this.nextCost = x;
	}
	
	public int GetRating(){
		return this.rating;
	}
	
	public void SetRating(int x /*in, product rating*/){
		this.rating = x;
	}
	
	public int GetNumInStock(){
		return this.numInStock;
	}
	
	public void SetNumInStock(int x /*in, number of products in stock*/){
		this.numInStock = x;
	}
	
	public int GetCategoryID(){
		return this.categoryID;
	}
	
	public void SetCategoryID(int x /*in, category*/){
		this.categoryID = x;
	}
	
	public String getPicture() {
		if (this.pictureBlob != null)
			return "data:image/jpeg;base64, " + Base64.getEncoder().encodeToString(this.pictureBlob);
		else
			return "";
	}

	public void setPicture(byte[] picture) {
		this.pictureBlob = picture;
	}


	public int getReviewCount() {
		return reviewCount;
	}


	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	
	
}

