package com.example.receiveservice.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.receiveservice.model.Order;
import com.example.receiveservice.service.ReceiveOrderService;

@RestController
public class ReceiveOrderController {
	
	@Autowired
	ReceiveOrderService service;

	private static final Logger logger = Logger.getLogger(ReceiveOrderController.class);

	@PostMapping("/saveorder")
	public String saveOrder(@RequestBody Order serviceToken) {
		
		service.validateToken(serviceToken);
		
		return "isValid";
	}

}
