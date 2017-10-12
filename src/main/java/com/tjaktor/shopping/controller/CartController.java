package com.tjaktor.shopping.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjaktor.shopping.entity.Cart;
import com.tjaktor.shopping.entity.Item;
import com.tjaktor.shopping.service.CartService;
import com.tjaktor.shopping.service.EventPropagation;

import com.tjaktor.shopping.exception.NotFoundException;

@Controller
public class CartController {
	
	private CartService cartService;
	private EventPropagation eventPropagation;
	
	/**
	 * @param cartService CartService - Service for getting and saving carts.
	 * @param eventPropagation EventPropagation - Service to notify logged in users on events (etc. item was added, edited or collected).
	 */
	@Autowired
	public CartController(CartService cartService, EventPropagation eventPropagation) {
		this.cartService = cartService;
		this.eventPropagation = eventPropagation;
	}
	
	
	/**
	 * Shows a list of available carts
	 * 
	 * @param model Model
	 * @return String carts/carts (template)
	 */
	@RequestMapping(value = "/carts", method = RequestMethod.GET)
	public String listCarts(Model model) {
		
		Iterable<Cart> carts = this.cartService.listOfCarts();
		
		// For the 'create a new cart' -form. POST -method may send a {@link Cart} with it's {@link BindingResult} as an flash attribute.
		if( ! model.containsAttribute("cart")) {
			model.addAttribute("cart", new Cart());
		}
		
		model.addAttribute("carts", carts);
		return "carts/carts";
	}
	
	
	/**
	 * Create a new cart. Uses PRG (POST-Redirect-GET) -pattern. If user's form input has errors, redirect to the cart overview page (where the 'create a new cart -form' is) with the error data. 
	 * 
	 * @param cart Cart - The cart entity
	 * @param bindingResult BindingResult - Form validation results
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return String redirect to carts overview
	 */
	@RequestMapping(value = "/carts", method = RequestMethod.POST)
	public String createCart(@ModelAttribute("cart") @Valid Cart cart, BindingResult bindingResult, 
			RedirectAttributes redirectAttr, Model model) {
		
		if (bindingResult.hasErrors()) {
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.cart", bindingResult);
			redirectAttr.addFlashAttribute("cart", cart);
			return "redirect:/carts";
		}

		this.cartService.createCart(cart);
		this.eventPropagation.propagateEvent();
		
		return "redirect:/carts";
	}
	
	
	/**
	 * Delete a cart by an ID.
	 * 
	 * @param cartId Long - The ID of the cart to be deleted
	 * @param model Model
	 * @return String redirect to carts overview
	 */
	@RequestMapping(value = "/carts/deletecart")
	public String deleteCart(@RequestParam(value = "deletecart") Long cartId, Model model) {

		this.cartService.deleteCart(cartId);
		this.eventPropagation.propagateEvent();
		
		return "redirect:/carts";
	}
	
	
	/**
	 * Get list of products in a cart.
	 * 
	 * @param cartId Long - The ID of the cart
	 * @param model Model
	 * @throws NotFoundException if cart with ID not found
	 * @return String carts/cartdata (template)
	 */
	@RequestMapping(value = "/carts/{cartId}/products", method = RequestMethod.GET)
	public String listCartItems(@PathVariable("cartId") Long cartId, Model model) {
		
		Cart cart = this.cartService.findCartById(cartId);
		
		if (null == cart) {
			throw new NotFoundException("Cart not found. Requested ID: " + cartId);
		}
		
		model.addAttribute("cart", cart);

		if ( ! model.containsAttribute("item") ) {
			model.addAttribute("item", new Item());
		}
		
		return "carts/cartdata";
	}
	
	
	/**
	 * Add new item into a cart. Uses PRG (POST-Redirect-GET) -pattern if bindingResults contain errors.
	 * 
	 * @param item Item - A validated item from the form
	 * @param bindingResult BindingResult - Form validation results
	 * @param cartId Long - The ID of the cart
	 * @param model Model
	 * @param redirectAttr RedirectAttributes
	 * @return String redirect to a cart
	 */
	@RequestMapping(value = "/carts/{cartId}/products/newitem", method = RequestMethod.POST)
	public String createItem(@ModelAttribute("item") @Valid Item item, BindingResult bindingResult, 
			@PathVariable("cartId") Long cartId, Model model, RedirectAttributes redirectAttr) {
		
		if ( bindingResult.hasErrors()) {
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.item", bindingResult);
			redirectAttr.addFlashAttribute("item", item);
			return "redirect:/carts/{cartId}/products";
		}
		
		this.cartService.createItem(cartId, item);
		this.eventPropagation.propagateEvent();
		
		return "redirect:/carts/{cartId}/products";
	}
	
	
	/**
	 * Edit item in a cart.
	 * 
	 * @param item Item - The product to be edited
	 * @param bindingResult BindingResult - Form validation results
	 * @param cartId Long - The ID of the cart the product belongs to 
	 * @return String Redirect to the requested cart 
	 */
	@RequestMapping(value = "/carts/{cartId}/products/{itemPathId}/edit", method = RequestMethod.POST)
	public String updateItem(@Valid @ModelAttribute Item item, BindingResult bindingResult, 
			@PathVariable("cartId") Long cartId, RedirectAttributes redirectAttr) {

		if ( bindingResult.hasErrors() ) {
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.item", bindingResult);
			redirectAttr.addFlashAttribute("itemError", item);
			return "redirect:/carts/{cartId}/products";
		}

		this.cartService.updateItem(cartId, item);
		this.eventPropagation.propagateEvent();
		
		return "redirect:/carts/{cartId}/products";
	}

	
	/**
	 * Delete requested product from a cart.
	 * 
	 * @param itemId Long - The ID of the product to be deleted
	 * @param cartId Long - The cart ID from where the product is being deleted
	 * @return String Redirect to the requested cart
	 */
	@RequestMapping(value = "/carts/{cartId}/products/{itemPathId}/delete", method = RequestMethod.POST)
	public String deleteItem(@RequestParam(value = "deleteItemId") Long itemId, 
			@RequestParam(value = "cartId") Long cartId) {

		this.cartService.deleteItem(cartId, itemId);
		this.eventPropagation.propagateEvent();
		
		return "redirect:/carts/{cartId}/products";
	}
	
	
	/**
	 * Show the item collecting -page
	 * 
	 * @param cartId Long - The ID of the cart being collected
	 * @param model Model
	 * @throws NotFoundException if cart not found
	 * @return String carts/collect (template)
	 */
	@RequestMapping(value = "/carts/{cartId}/collect", method = RequestMethod.GET)
	public String collectCart(@PathVariable(value = "cartId") Long cartId, Model model) {
		
		Cart cart = this.cartService.findCartById(cartId);
		
		if (null == cart) {
			throw new NotFoundException("Cart not found. Requested ID: " + cartId);
		}
		
		model.addAttribute("cart", cart);
		return "carts/collect";
	}
	
	
	/**
	 * Process request to mark item collected.
	 * 
	 * @param itemId Long - The ID of the product to be marked collected
	 * @param cartId Long - The ID of the cart the product belongs to
	 * @param model Model
	 * @return String redirect to the cart that owns the item 
	 */
	@RequestMapping(value = "/carts/{cartId}/collect", method = RequestMethod.POST)
	public String collectCartItem(@RequestParam("itemId") Long itemId, @PathVariable("cartId") Long cartId, Model model) {

		this.cartService.collectItem(cartId, itemId);
		this.eventPropagation.propagateEvent();
		
		return "redirect:/carts/{cartId}/collect";
	}
}
