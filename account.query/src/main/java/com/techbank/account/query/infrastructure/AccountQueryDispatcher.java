package com.techbank.account.query.infrastructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techbank.cqrs.core.domain.BaseEntity;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import com.techbank.cqrs.core.queries.BaseQuery;
import com.techbank.cqrs.core.queries.QueryHandlerMethod;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountQueryDispatcher implements QueryDispatcher {
	@SuppressWarnings("rawtypes")
	private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> ROUTES = new HashMap<>();

	@Override
	public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
		var handlers = ROUTES.computeIfAbsent(type, c -> new LinkedList<>());
		handlers.add(handler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U extends BaseEntity> List<U> send(BaseQuery query) {
		var handlers = ROUTES.get(query.getClass());
		if(handlers == null || handlers.isEmpty()) {
			throw new RuntimeException("No query handler was registered");
		}
		if(handlers.size() > 1) {
			throw new RuntimeException("Cannot send query to more than one handler");
		}
		return handlers.get(0).handle(query);
	}

}
