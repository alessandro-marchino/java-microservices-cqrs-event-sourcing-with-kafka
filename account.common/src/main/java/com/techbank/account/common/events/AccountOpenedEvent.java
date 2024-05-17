package com.techbank.account.common.events;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.events.BaseEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {

	private String accountHolder;
	private AccountType accountType;
	private OffsetDateTime createdDate;
	private BigDecimal openingBalance;
	
}
