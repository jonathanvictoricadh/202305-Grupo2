package com.jmg.checkagro.customer.repository;

import com.jmg.checkagro.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Transactional
    @Modifying
    @Query("update Customer c set c.active = ?1 where c.id = ?2")
    void updateActive(Boolean active, Long id);


    Optional<Customer> findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber);

    Optional<Customer> findByIdAndActive(Long id, boolean b);
}
