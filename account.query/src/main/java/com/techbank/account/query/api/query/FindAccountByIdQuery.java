package com.techbank.account.query.api.query;

import com.techbank.cqrs.core.queries.BaseQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FindAccountByIdQuery extends BaseQuery {
	private String id;
}
