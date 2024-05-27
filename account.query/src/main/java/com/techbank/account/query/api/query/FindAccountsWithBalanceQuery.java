package com.techbank.account.query.api.query;

import java.math.BigDecimal;

import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.cqrs.core.queries.BaseQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FindAccountsWithBalanceQuery extends BaseQuery {
	private EqualityType equalityType;
	private BigDecimal balance;
}
