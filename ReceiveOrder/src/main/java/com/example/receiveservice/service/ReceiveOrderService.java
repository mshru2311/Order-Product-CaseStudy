package com.example.receiveservice.service;

import com.example.receiveservice.model.Order;

public interface ReceiveOrderService {
	
	void validateToken(Order serviceToken);

}
