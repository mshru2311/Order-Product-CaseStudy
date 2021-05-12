package com.example.appid.service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.appid.controller.AccountLoginController;
import com.example.appid.model.Order;
import com.example.appid.model.Product;
import com.example.appid.repository.ProductRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	ProductRepository repo;

	Logger logger = LogManager.getLogger(AccountLoginController.class);

	String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
	Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

	@Override
	public List<Product> getProductDetail() {
		return repo.findAll();
	}

	@Override
	public String getTransactionJwtToken(Order orderEntity) {
		Instant now = Instant.now();
		String transactionToken = Jwts.builder()
				.claim("productName", orderEntity.getProductName())
				.claim("quantity", orderEntity.getQuantity())
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(now)).setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
				.compact();
		return transactionToken;
	}

	@Override
	public String getServiceToken(String trasactionToken, String userToken) {
		Instant now = Instant.now();
        String serviceToken = Jwts.builder()
        		.claim("transactionToken",trasactionToken)
        		.claim("userToken", userToken)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();
		return serviceToken;
	}

	@Override
	public boolean updateProductQuantity(Order orderEntity) {
		Product productResponse = repo.findByProductName(orderEntity.getProductName());
		if(!Objects.isNull(productResponse)) {
			if(productResponse.getQuantity()>=orderEntity.getQuantity()) {
				int num=productResponse.getQuantity()-orderEntity.getQuantity();
				if(num>0) {
					productResponse.setQuantity(num);
					repo.save(productResponse);
					return true;
				}else {
					repo.delete(productResponse);
					return true;
				}
			}else {
				//repo.delete(productResponse);
				return false;
			}
		}
		return false;
		
	}

}
