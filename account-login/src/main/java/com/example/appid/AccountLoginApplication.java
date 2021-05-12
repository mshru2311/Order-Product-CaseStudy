package com.example.appid;

import javax.annotation.PostConstruct;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.appid.model.Product;
import com.example.appid.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class AccountLoginApplication {
	
	@Autowired
	ProductRepository repository;
    
    public static void main(String[] args) {
        SpringApplication.run(AccountLoginApplication.class, args);
    }
    
    @Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	
    @PostConstruct
	public void insertData() {
    	repository.save(new Product("Kiwis",10,30.00));
    	repository.save(new Product("Pineapples",20,40.00));
    	repository.save(new Product("Oranges",50,85.50));
    	repository.save(new Product("Mangoes",30,126.20));
    	repository.save(new Product("Apples",20,162.20));


	}
}
