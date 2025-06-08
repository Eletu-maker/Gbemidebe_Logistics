package org.example.data.repository;

import org.example.data.model.Sender;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface Senders extends MongoRepository<Sender,String> {
    boolean existsByEmail(String email);
    Sender findByEmail(String email);
}
