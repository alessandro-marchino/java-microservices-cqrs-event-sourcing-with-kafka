package com.techbank.cqrs.core.infrastructure;

import java.util.List;

import com.techbank.cqrs.core.domain.BaseEntity;
import com.techbank.cqrs.core.queries.BaseQuery;
import com.techbank.cqrs.core.queries.QueryHandlerMethod;

public interface QueryDispatcher {
	<T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
	<U extends BaseEntity> List<U> send(BaseQuery query);
}
