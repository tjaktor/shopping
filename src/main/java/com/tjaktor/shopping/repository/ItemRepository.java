package com.tjaktor.shopping.repository;

import org.springframework.data.repository.CrudRepository;

import com.tjaktor.shopping.entity.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {

}
