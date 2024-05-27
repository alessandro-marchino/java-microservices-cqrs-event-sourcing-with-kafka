package com.techbank.account.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.techbank.account.query.api.query.FindAccountByHolderQuery;
import com.techbank.account.query.api.query.FindAccountByIdQuery;
import com.techbank.account.query.api.query.FindAccountsWithBalanceQuery;
import com.techbank.account.query.api.query.FindAllAccountsQuery;
import com.techbank.account.query.api.query.QueryHandler;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class QueryApplication {
	private final QueryDispatcher queryDispatcher;
	private final QueryHandler queryHandler;

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		queryDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountsWithBalanceQuery.class, queryHandler::handle);
	}

}
