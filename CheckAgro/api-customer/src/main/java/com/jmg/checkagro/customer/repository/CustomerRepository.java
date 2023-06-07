package com.jmg.checkagro.customer.repository;

import com.jmg.checkagro.customer.model.Customer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer,String> {
    @Transactional
    //@Modifying
    @Query("update Customer c set c.active = ?1 where c.id = ?2")
    void updateActive(Boolean active, String id);


    Optional<Customer> findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber);

    Optional<Customer> findByIdAndActive(String id, boolean b);
}
