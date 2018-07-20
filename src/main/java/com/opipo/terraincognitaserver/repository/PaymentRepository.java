package com.opipo.terraincognitaserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.dto.Payment;

public interface PaymentRepository extends MongoRepository<Payment, Long> {

}
