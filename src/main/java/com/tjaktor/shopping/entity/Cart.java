package com.tjaktor.shopping.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;

import com.tjaktor.shopping.exception.ItemNotFoundException;

@Entity
public class Cart implements Serializable {
	
	private static final long serialVersionUID = 2584574176365848586L;

	@Id
	@GeneratedValue
	private Long cartId;
	
	@NotBlank(message = "NotBlank.cart.name")
	private String name;
		
	@OneToMany( mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<Item> items;
	
	private int numberOfitems;

	@CreatedDate @Column(columnDefinition = "TIMESTAMP")
	private Date createdOn;

	public Cart() {
		
	}
	
	@Override
	public String toString ( )
	{
		return "Cart [" + this.cartId + ", " + this.name + ", " + this.createdOn + "]";
	}
	
	public Long getCartId() {
		return this.cartId;
	}

	public void setCartId(Long id) {
		this.cartId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
		this.numberOfitems = this.items.size();
	}
	
	public void addItem(Item item) {
		item.setCart(this);
		this.items.add(item);
		this.numberOfitems++;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	/**
	 * Return true if items-list is empty.
	 * 
	 * @return boolean
	 */
	public boolean isItemsEmpty() {
		return this.items.isEmpty();
	}
	
	/**
	 * Update an item with new data.
	 * 
	 * @param newItemData
	 */
	public void updateItem(Item newItemData) {
		
		Long itemId = newItemData.getItemId();
		
		boolean itemFound = false;
		
		for (Item anItem : this.items) {
			if (itemId.equals(anItem.getItemId())) {
				itemFound = true;
				anItem.setItemName(newItemData.getItemName());
				anItem.setItemDescription(newItemData.getItemDescription());
				anItem.setItemAmount(newItemData.getItemAmount());
				anItem.setItemCollected(newItemData.getItemCollected());
				break;
			}
		}
		
		if ( ! itemFound) {
			throw new ItemNotFoundException("Item not updated, requested item not found. Item ID: " + itemId);
		}
	}	
	
	/**
	 * Remove an item from the items-list.
	 * 
	 * @param itemId
	 */
	public void removeItem(Long itemId) {
		this.items.removeIf(item -> item.getItemId() == itemId);
		this.numberOfitems--;
	}
	
	/**
	 * Mark an item collected by itemId.
	 * 	
	 * @param itemId
	 */
	public void changeCollectedStatus(Long itemId) {
		
		for (Item item : this.items ){
			if (itemId.equals(item.getItemId())) {
				item.changeCollectedStatus();				
				break;
			}
		}
	}
	
	/**
	 * Return true if all the products in the cart are collected.
	 * 
	 * @return boolean
	 */
	public boolean isAllItemsCollected() {
		long numberOfCollectedItems = this.items.stream().filter(item -> item.getItemCollected() == true).count();
				
		if (this.numberOfitems > 0 && this.numberOfitems == numberOfCollectedItems) {
			return true;
		} else {
			return false;
		}
	}
}
