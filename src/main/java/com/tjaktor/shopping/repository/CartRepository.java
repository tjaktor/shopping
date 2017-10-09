package com.tjaktor.shopping.repository;

import org.springframework.data.repository.CrudRepository;

import com.tjaktor.shopping.entity.Cart;

public interface CartRepository extends CrudRepository<Cart, Long> {

}
