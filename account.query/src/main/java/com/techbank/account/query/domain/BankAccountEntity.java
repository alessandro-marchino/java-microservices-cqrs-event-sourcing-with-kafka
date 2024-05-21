package com.techbank.account.query.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BankAccountEntity extends BaseEntity {

	@Id
	private String id;
	private String accountHolder;
	private OffsetDateTime creationDate;
	private AccountType accountType;
	private BigDecimal balance;
}
