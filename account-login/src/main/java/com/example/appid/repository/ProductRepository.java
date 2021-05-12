package com.example.appid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.appid.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	Product findByProductName(String productName);

}
