package controller;
import controller.CartItem;
import controller.Cart;

public class WishList extends Cart{
	public WishList() {
		super(); //Call Cart's constructor
	}
	
	public void AddItems2CartObj(Cart c){
		CartItem tempItem;
		for (int i=0; i < this.items.size(); i++){
			tempItem = this.items.get(i);
			for (int j = 0; j < tempItem.GetQuantity(); j++){
				c.AddItem(tempItem.GetProductID());
			}
		}
	}
	
}
