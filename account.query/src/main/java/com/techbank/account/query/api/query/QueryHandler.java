package com.techbank.account.query.api.query;

import java.util.List;

import com.techbank.cqrs.core.domain.BaseEntity;

public interface QueryHandler {
	List<BaseEntity> handle(FindAllAccountsQuery query);
	List<BaseEntity> handle(FindAccountByIdQuery query);
	List<BaseEntity> handle(FindAccountByHolderQuery query);
	List<BaseEntity> handle(FindAccountsWithBalanceQuery query);
}
