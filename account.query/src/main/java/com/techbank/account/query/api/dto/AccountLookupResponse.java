package com.techbank.account.query.api.dto;

import java.util.List;

import com.techbank.account.common.dto.BaseResponse;
import com.techbank.account.query.domain.BankAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLookupResponse extends BaseResponse {

	private List<BankAccount> accounts;

	public AccountLookupResponse(String message) {
		super(message);
	}
}
