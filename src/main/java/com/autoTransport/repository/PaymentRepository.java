package com.autoTransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autoTransport.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
}