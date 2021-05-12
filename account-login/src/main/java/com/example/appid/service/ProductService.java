package com.example.appid.service;

import java.util.List;

import com.example.appid.model.Order;
import com.example.appid.model.Product;

public interface ProductService {


	List<Product> getProductDetail();

	String getTransactionJwtToken(Order orderEntity);

	String getServiceToken(String trasactionToken, String userToken);

	boolean updateProductQuantity(Order orderEntity);
}
