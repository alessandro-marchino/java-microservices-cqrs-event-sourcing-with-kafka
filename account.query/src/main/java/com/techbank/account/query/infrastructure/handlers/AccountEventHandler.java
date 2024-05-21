package com.techbank.account.query.infrastructure.handlers;

import org.springframework.stereotype.Service;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.account.query.domain.BankAccount;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountEventHandler implements EventHandler {
	private final AccountRepository accountRepository;

	@Override
	public void on(AccountOpenedEvent event) {
		var bankAccount = BankAccount.builder()
				.id(event.getId())
				.accountHolder(event.getAccountHolder())
				.creationDate(event.getCreatedDate())
				.accountType(event.getAccountType())
				.balance(event.getOpeningBalance())
				.build();
		accountRepository.save(bankAccount);
	}

	@Override
	public void on(FundsDepositedEvent event) {
		var bankAccount = accountRepository.findById(event.getId());
		bankAccount.ifPresent(ba -> {
			var currentBalance = ba.getBalance();
			currentBalance = currentBalance.add(event.getAmount());
			ba.setBalance(currentBalance);
			accountRepository.save(ba);
		});
	}

	@Override
	public void on(FundsWithdrawnEvent event) {
		var bankAccount = accountRepository.findById(event.getId());
		bankAccount.ifPresent(ba -> {
			var currentBalance = ba.getBalance();
			currentBalance = currentBalance.subtract(event.getAmount());
			ba.setBalance(currentBalance);
			accountRepository.save(ba);
		});
	}

	@Override
	public void on(AccountClosedEvent event) {
		accountRepository.deleteById(event.getId());
	}

}
