package controller;

import java.util.*;
import controller.CartItem;

public class Cart {
	
	private final byte GROUND_SHIPPING = 1;
	private final byte TWO_DAY_SHIPPING = 2;
	private final byte NEXT_DAY_SHIPPING = 3;
	
	private final float TAX_PERCENTAGE = 0.07f;
	protected List<CartItem> items;
	private float cost;
	private float shippingCost;
	private byte shippingMethod;
	
	public Cart(){
		this.cost = 0.0f;
		this.shippingCost = 0.0f;
		items = new ArrayList<CartItem>();
		shippingMethod = GROUND_SHIPPING; //Default shipping method is ground shipping
	}
	
	public List<CartItem> GetAllItems(){
		return this.items;
	}
	
	public int NumItems(){
		int numItemsInCart = 0;
		
		for (int i=0; i < this.items.size(); i++){
			numItemsInCart += this.items.get(i).GetQuantity();
		}
		
		return numItemsInCart;
	}
	
	public void AddItem(int productID){
		//First check to see if the item already exists in the cart
		CartItem tempItem;
		Boolean itemAlreadyInCart = false;
		
		for (int i=0; i < this.items.size(); i++){
			if (this.items.get(i).GetProductID() == productID){ //If the item already exists in the cart
				tempItem = this.items.get(i); //Obtain the item object currently in the cart
				this.UpdateQuantity(tempItem.GetProductID(), (tempItem.GetQuantity() + 1)); //Update the quantity of the item in the cart
				itemAlreadyInCart = true; //Flag that indicates the item already existed in the cart
				break; //Exit the loop
			}
		}
		
		if (!itemAlreadyInCart){ //If the item didn't already exist in the cart
			tempItem = new CartItem(productID,1); //Build the cart item object
			this.items.add(tempItem); //Add the cart item object to the cart
		}
	}
	
	public void AddItem(CartItem newItem){
		this.AddItem(newItem.GetProductID());
	}
	
	public void RemoveItem(int productID){
		for (int i=0; i < this.items.size(); i++){
			if (this.items.get(i).GetProductID() == productID){
				this.items.remove(i);
			}
		}
	}
	
	public void UpdateQuantity(int productID, int qty){ 
		CartItem tempItem;
		
		if (qty <= 0){
			this.RemoveItem(productID);
			return;
		}
		
		for (int i=0; i < this.items.size(); i++){
			if (this.items.get(i).GetProductID() == productID){
				tempItem = this.items.get(i);
				tempItem.SetQuantity(qty);
				this.items.set(i, tempItem);
			}
		}
	}
	
	public void UpdateCostAndShippingCost(){
		this.cost = 0.0f;
		this.shippingCost = 0.0f;
		int qty = 0;
		Product prod; //Prevent null exception
		float productShippingCost = -1.0f;
		
		for (int i=0; i < this.items.size(); i++){
			qty = this.items.get(i).GetQuantity();
			prod = AuthDAO.getProductById(this.items.get(i).GetProductID());
			this.cost += prod.GetPrice() * qty;
			switch (this.shippingMethod){ //Determine the shipping method and cost
				case GROUND_SHIPPING:
					productShippingCost = prod.GetGroundShippingCost();
					break;
				case TWO_DAY_SHIPPING:
					productShippingCost = prod.GetTwoDayShippingCost();
					break;
				case NEXT_DAY_SHIPPING:
					productShippingCost = prod.GetNextDayShippingCost();
					break;
			}
			this.shippingCost += productShippingCost * qty;
		}
	}
	
	public float GetCost(){
		return cost;
	}
	
	public float GetShippingCost(){
		return this.shippingCost;
	}
	
	public float GetTax(){		
		return ((this.GetCost() + this.GetShippingCost()) * this.TAX_PERCENTAGE);
	}
	
	public float GetTotal(){
		return (this.GetCost() + this.GetTax() + this.GetShippingCost());
	}
	
	public byte GetShippingMethod(){
		return this.shippingMethod;
	}
	
	public void SetShippingMethod(byte x /*Shipping method*/){
		this.shippingMethod = x;
	}
	
}