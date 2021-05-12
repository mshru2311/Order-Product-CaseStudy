package com.example.receiveservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.receiveservice.model.TokenEntity;

public interface ReceiveOrderRepo extends JpaRepository<TokenEntity, String> {

}
