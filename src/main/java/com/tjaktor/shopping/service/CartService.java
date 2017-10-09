package com.tjaktor.shopping.service;

import org.springframework.stereotype.Service;

import com.tjaktor.shopping.entity.Cart;
import com.tjaktor.shopping.entity.Item;
import com.tjaktor.shopping.repository.CartRepository;

@Service
public interface CartService {
	
	
	/**
	 * Get list of carts from the database.
	 * 
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException.
	 * @return Iterable list of {@link Cart}s or {@literal empty}
	 */
	public Iterable<Cart> listOfCarts();
	
	
	/**
	 * Get a cart by ID.
	 * 
	 * @param Long cartId
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 * @return Cart or {@literal null} if not found
	 */
	public Cart findCartById(Long cartId);
	
	
	/**
	 * Create a new cart and insert it into the database.
	 * 
	 * @param Cart cart
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 */
	public void createCart(Cart cart);
	
	
	/**
	 * Delete a cart by it's ID.
	 * 
	 * @param Long cartId
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 */
	public void deleteCart(Long cartId);
	
	
	/**
	 * Create a new item.
	 * 
	 * @param Long cartId - The ID of the cart the item belongs to
	 * @param Item item
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 */
	public void createItem(Long cartId, Item item);
	
	
	/**
	 * Update an item.
	 * 
	 * @param Long cartId
	 * @param Item item
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 */
	public void updateItem(Long cartId, Item item);
	
	
	/**
	 * Delete an item.
	 * 
	 * @param Long cartId
	 * @param Long itemId
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 */
	public void deleteItem(Long cartId, Long itemId);
	
	
	/**
	 * Change the collected status of an item.
	 * 
	 * @param Long cartId
	 * @param Long itemId
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 */
	public void collectItem(Long cartId, Long itemId);
}
