package com.techbank.cqrs.core.queries;

import java.util.List;

import com.techbank.cqrs.core.domain.BaseEntity;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
	List<BaseEntity> handle(T query);
}
