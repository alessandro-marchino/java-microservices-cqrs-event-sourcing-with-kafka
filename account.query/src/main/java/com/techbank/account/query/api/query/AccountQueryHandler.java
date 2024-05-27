package com.techbank.account.query.api.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.cqrs.core.domain.BaseEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountQueryHandler implements QueryHandler {
	private AccountRepository accountRepository;

	@Override
	public List<BaseEntity> handle(FindAllAccountsQuery query) {
		return accountRepository.findAll().stream().map(el -> (BaseEntity) el).toList();
	}

	@Override
	public List<BaseEntity> handle(FindAccountByIdQuery query) {
		var bankAccount = accountRepository.findById(query.getId());
		if(bankAccount.isEmpty()) {
			return null;
		}
		return List.of((BaseEntity)bankAccount.get());
	}

	@Override
	public List<BaseEntity> handle(FindAccountByHolderQuery query) {
		var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
		if(bankAccount.isEmpty()) {
			return null;
		}
		return List.of((BaseEntity)bankAccount.get());
	}

	@Override
	public List<BaseEntity> handle(FindAccountsWithBalanceQuery query) {
		var bankAccountsList = EqualityType.GREATER_THAN.equals(query.getEqualityType())
				? accountRepository.findByBalanceGreaterThan(query.getBalance())
				: accountRepository.findByBalanceLessThan(query.getBalance());
		return bankAccountsList.stream().map(el -> (BaseEntity) el).toList();
	}

}

