package com.example.receiveservice.service;

import java.security.Key;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.receiveservice.model.Order;
import com.example.receiveservice.model.TokenEntity;
import com.example.receiveservice.repository.ReceiveOrderRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ReceiveOrderServiceImp implements ReceiveOrderService{
	
	@Autowired
	ReceiveOrderRepo repo;

	@Override
	public void validateToken(Order serviceToken) {
		String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
		
		Claims serviceTkn = Jwts.parserBuilder().setSigningKey(hmacKey).build()
				.parseClaimsJws(serviceToken.getServiceToken()).getBody();
		String transactionTkn = serviceTkn.get("transactionToken",String.class);
		String userToken = serviceTkn.get("userToken",String.class);
		
		Claims transactionToken = Jwts.parserBuilder().setSigningKey(hmacKey).build()
				.parseClaimsJws(transactionTkn).getBody();
		
		saveTokeninDB(serviceToken.getServiceToken(),transactionTkn,userToken);
	}

	private void saveTokeninDB(String serviceToken, String transactionTkn, String userToken) {
		TokenEntity entity = new TokenEntity();
		entity.setServiceToken(serviceToken);
		entity.setTransactionToken(transactionTkn);
		entity.setUserToken(userToken);
		repo.save(entity);
		
	}
}
