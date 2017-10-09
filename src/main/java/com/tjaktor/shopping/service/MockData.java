package com.tjaktor.shopping.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjaktor.shopping.entity.Cart;
import com.tjaktor.shopping.entity.Item;
import com.tjaktor.shopping.repository.CartRepository;

@Service
public class MockData {
	
	private CartRepository cartRepository;
	
	@Autowired
	public MockData(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}
	
	@PostConstruct
	public void loadMockData() {
		Cart newCart1 = new Cart();
		newCart1.setName("First list");
		newCart1.setCreatedOn(new Date());
		
		List<Item> itemlist1 = new ArrayList<>();
		
		Item newItem1 = new Item();
		newItem1.setItemName("Milk");
		newItem1.setItemDescription("The white one with a cow picture");
		newItem1.setItemAmount(2);
		newItem1.setCart(newCart1);
		
		
		Item newItem2 = new Item();
		newItem2.setItemName("Salad");
		newItem2.setItemDescription("Green");
		newItem2.setItemAmount(1);
		newItem2.setCart(newCart1);
		
		itemlist1.add(newItem1);
		itemlist1.add(newItem2);
		newCart1.setItems(itemlist1);
		this.cartRepository.save(newCart1);
		
		Cart newCart2 = new Cart();
		newCart2.setName("Ostoslista");
		newCart2.setCreatedOn(new Date());
		
		List<Item> itemlist2 = new LinkedList<>();
		
		Item newItem3 = new Item();
		newItem3.setItemName("Jauheliha");
		newItem3.setItemDescription("10%");
		newItem3.setItemAmount(1);
		newItem3.setCart(newCart2);
		
		Item newItem4 = new Item();
		newItem4.setItemName("Spagettia");
		newItem4.setItemDescription("Täysjyvää");
		newItem4.setItemAmount(2);
		newItem4.setCart(newCart2);
		
		itemlist2.add(newItem3);
		itemlist2.add(newItem4);
		newCart2.setItems(itemlist2);
		this.cartRepository.save(newCart2);
	}
	
}
