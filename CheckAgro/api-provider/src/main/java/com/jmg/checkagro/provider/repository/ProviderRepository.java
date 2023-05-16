package com.jmg.checkagro.provider.repository;

import com.jmg.checkagro.provider.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Long> {
    @Transactional
    @Modifying
    @Query("update Provider c set c.active = ?1 where c.id = ?2")
    void updateActive(Boolean active, Long id);


    Optional<Provider> findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber);

    Optional<Provider> findByIdAndActive(Long id, boolean b);
}
