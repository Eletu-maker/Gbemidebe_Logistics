package org.example.data.repository;

import org.example.data.model.DispatchDriver;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DispatchDrivers extends MongoRepository<DispatchDriver,String> {
    boolean existsByEmail(String email);
    DispatchDriver findByEmail(String email);
    DispatchDriver findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);
    DispatchDriver getDispatchDriverByAvailable(boolean available);

    List<DispatchDriver> findAllByLogin(boolean login);
    List<DispatchDriver> findAllByAvailable(boolean available);
}
