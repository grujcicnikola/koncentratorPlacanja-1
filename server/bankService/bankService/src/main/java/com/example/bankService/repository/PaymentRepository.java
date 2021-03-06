package com.example.bankService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bankService.domain.Payment;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>  {

	List<Payment> findByMerchantId(String merchantId);

	Optional<Payment> findOneByPaymentId(String paymentId);

}
