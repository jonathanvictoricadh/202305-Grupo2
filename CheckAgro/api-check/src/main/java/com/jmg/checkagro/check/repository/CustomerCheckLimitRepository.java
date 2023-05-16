package com.jmg.checkagro.check.repository;

import com.jmg.checkagro.check.model.CustomerCheckLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCheckLimitRepository extends JpaRepository<CustomerCheckLimit, CustomerCheckLimit.CustomerCheckLimitId > {
}
