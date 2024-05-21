package com.techbank.account.query.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techbank.cqrs.core.domain.BaseEntity;

@Repository
public interface AccountRepository extends JpaRepository<BankAccount, String> {
	Optional<BankAccount> findByAccountHolder(String accountHolder);
	List<BaseEntity> findByBalanceGreaterThan(BigDecimal balance);
	List<BaseEntity> findByBalanceLessThan(BigDecimal balance);
}
