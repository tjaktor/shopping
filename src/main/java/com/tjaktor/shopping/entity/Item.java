package com.tjaktor.shopping.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Item implements Serializable {
	
	private static final long serialVersionUID = 3798027626309716931L;

	@Id
	@GeneratedValue
	private Long itemId;
	
	@NotBlank(message = "NotBlank.item.itemname")
	private String itemName;
	
	@com.fasterxml.jackson.annotation.JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Cart cart;
	
	private String itemDescription;
	
	@Min(1)
	private int itemAmount = 1;
	
	private boolean itemCollected = false;
	
	public Item() {
		
	}

	@Override
	public String toString ( )
	{
		return "[Item ID: " + this.itemId + ", description: " + this.itemDescription + ", amount: " + this.itemAmount + ", cart ID:" + cart.getCartId() + ", collected: " + this.itemCollected + "]";
	}
	
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public int getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(int itemAmount) {
		this.itemAmount = itemAmount;
	}

	public boolean getItemCollected() {
		return this.itemCollected;
	}
	
	public boolean isItemCollected() {
		return itemCollected;
	}

	public void setItemCollected(boolean collected) {
		this.itemCollected = collected;
	}
	
	/**
	 * Change item collected {@literal boolean} status
	 */
	public void changeCollectedStatus() {
		
		if (false == this.itemCollected) {
			this.itemCollected = true;
		} else {
			this.itemCollected = false;
		}
	}
}
