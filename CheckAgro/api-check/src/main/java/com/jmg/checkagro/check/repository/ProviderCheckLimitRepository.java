package com.jmg.checkagro.check.repository;

import com.jmg.checkagro.check.model.ProviderCheckLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderCheckLimitRepository extends JpaRepository<ProviderCheckLimit, ProviderCheckLimit.ProviderCheckLimitId> {
}
