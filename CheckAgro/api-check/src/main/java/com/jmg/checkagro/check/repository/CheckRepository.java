package com.jmg.checkagro.check.repository;

import com.jmg.checkagro.check.model.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CheckRepository extends JpaRepository<Check,Long> {
    List<Check> findByDocumentTypeCustomerAndDocumentValueCustomer(String typeDocument, String valueDocument);

    List<Check> findByDocumentTypeProviderAndDocumentValueProvider(String typeDocument, String valueDocument);
}
