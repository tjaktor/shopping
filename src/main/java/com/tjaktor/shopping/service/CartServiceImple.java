package com.tjaktor.shopping.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjaktor.shopping.entity.Cart;
import com.tjaktor.shopping.entity.Item;
import com.tjaktor.shopping.exception.CartServiceException;
import com.tjaktor.shopping.exception.NotFoundException;
import com.tjaktor.shopping.repository.CartRepository;

@Service
public class CartServiceImple implements CartService {
	
	private CartRepository cartRepository;
	
	@Autowired
	public CartServiceImple(CartRepository cartRepository) {
		
		this.cartRepository = cartRepository;
	}
	
	
	/**
	 * Get list of carts from the database.
	 * 
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException.
	 * @return Iterable list of {@link Cart}s or {@literal empty}
	 */
	@Transactional
	@Override
	public Iterable<Cart> listOfCarts() {
		
		try {
			return this.cartRepository.findAll();
			
		} catch (RuntimeException ex) {
			throw new CartServiceException("Cart service: " + ex.getMessage(), ex);
		}
	}
	

	/**
	 * Get a cart by ID.
	 * 
	 * @param Long cartId
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 * @return Cart or {@literal null} if not found
	 */
	@Transactional
	@Override
	public Cart findCartById(Long cartId) {
				
		try {
			validateId(cartId);
			Cart cart = this.cartRepository.findOne(cartId);
			return cart;
			
		} catch (RuntimeException ex) {
			throw new CartServiceException("Cart service: " + ex.getMessage(), ex);
		}
	}
	

	/**
	 * Create a new cart and insert it into the database.
	 * 
	 * @param Cart cart
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 */
	@Transactional
	@Override
	public void createCart(Cart cart) {
		
		try {
			cart.setCreatedOn(new Date());
			this.cartRepository.save(cart);
			
		} catch (RuntimeException ex) {
			throw new CartServiceException("Cart service: " + ex.getMessage(), ex);
		}
	}

	
	/**
	 * Delete a cart by it's ID.
	 * 
	 * @param Long cartId
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 */
	@Transactional
	@Override
	public void deleteCart(Long cartId) {
		
		try {
			validateId(cartId);
			this.cartRepository.delete(cartId);
			
		} catch (RuntimeException ex) {
			throw new CartServiceException("Cart service: " + ex.getMessage(), ex);
		}
	}

	
	/**
	 * Create a new item.
	 * 
	 * @param Long cartId - The ID of the cart the item belongs to
	 * @param Item item
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 */
	@Transactional
	@Override
	public void createItem(Long cartId, Item item) {

		try {
			validateId(cartId);
			Cart cart = this.cartRepository.findOne(cartId);

			if (null != cart) {
				cart.addItem(item);
				this.cartRepository.save(cart);
				
			} else {
				throw new NotFoundException("Cart not found, ID: " + cartId);
			}
			
		} catch (RuntimeException ex) {
			throw new CartServiceException("Cart service: " + ex.getMessage(), ex);
		}
	}
	
	
	/**
	 * Update an item.
	 * 
	 * @param Long cartId
	 * @param Item item
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 */
	@Transactional
	@Override
	public void updateItem(Long cartId, Item item) {
		
		try {
			validateId(cartId);
			Cart cart = this.cartRepository.findOne(cartId);

			if (null != cart) {
				cart.updateItem(item);
				this.cartRepository.save(cart);
				
			} else {
				throw new NotFoundException("Cart not found, ID: " + cartId);
			}
			
		} catch (RuntimeException ex) {
			throw new CartServiceException("Cart service: " + ex.getMessage(), ex);
		}
	}
	
	
	/**
	 * Delete an item.
	 * 
	 * @param Long cartId
	 * @param Long itemId
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 */
	@Transactional
	@Override
	public void deleteItem(Long cartId, Long itemId) {

		try {
			validateId(cartId);
			validateId(itemId);
			Cart cart = this.cartRepository.findOne(cartId);

			if (null != cart) {
				cart.removeItem(itemId);
				this.cartRepository.save(cart);
				
			} else {
				throw new NotFoundException("Cart not found, ID: " + cartId);
			}
			
		} catch (RuntimeException ex) {
			throw new CartServiceException("Cart service: " + ex.getMessage(), ex);
		}
	}

	
	/**
	 * Change the collected status of an item.
	 * 
	 * @param Long cartId
	 * @param Long itemId
	 * @throws CartServiceException if {@link CartRepository} throws RuntimeException or validation method throws an error.
	 */
	@Transactional
	@Override
	public void collectItem(Long cartId, Long itemId) {
		
		try {
			validateId(cartId);
			validateId(itemId);
			Cart cart = this.cartRepository.findOne(cartId);
			
			if (null != cart) {
				cart.changeCollectedStatus(itemId);
				
			} else {
				throw new NotFoundException("Cart not found, ID: " + cartId);
			}
			
		} catch (RuntimeException ex) {
			throw new CartServiceException("Cart service: " + ex.getMessage(), ex);
		}
	}
	
	
	/**
	 * Validate an id.
	 * 
	 * @param id Long
	 * @throws NullPointerException if ID null
	 */
	private void validateId(Long id) {
		
		if (null == id) {
			throw new NullPointerException("Null given as an argument (" + id + ").");
		}
	}
}
