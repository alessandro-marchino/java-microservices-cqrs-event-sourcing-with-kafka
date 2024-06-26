package com.techbank.account.cmd.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.cqrs.core.domain.AggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
	@Getter
	private boolean active;
	private BigDecimal balance;
	
	public AccountAggregate(OpenAccountCommand command) {
		raiseEvent(AccountOpenedEvent.builder()
				.id(command.getId())
				.accountHolder(command.getAccountHolder())
				.createdDate(OffsetDateTime.now())
				.accountType(command.getAccountType())
				.openingBalance(command.getOpeningBalance())
				.build());
	}
	
	public void apply(AccountOpenedEvent event) {
		this.id = event.getId();
		this.active = true;
		this.balance = event.getOpeningBalance();
	}
	
	public void depositFunds(BigDecimal amount) {
		if(!this.active) {
			throw new IllegalStateException("Funds cannot be deposited into a closed account");
		}
		if(BigDecimal.ZERO.compareTo(amount) >= 0) {
			throw new IllegalStateException("The deposit amount must be greater than zero");
		}
		raiseEvent(FundsDepositedEvent.builder()
				.id(this.id)
				.amount(amount)
				.build());
	}
	
	public void apply(FundsDepositedEvent event) {
		this.id = event.getId();
		this.balance = this.balance.add(event.getAmount());
	}
	
	public void withdrawAmount(BigDecimal amount) {
		if(!this.active) {
			throw new IllegalStateException("Funds cannot be withdrawn into a closed account");
		}
		if(BigDecimal.ZERO.compareTo(amount) >= 0) {
			throw new IllegalStateException("The withdraw amount must be greater than zero");
		}
		if(this.balance.compareTo(amount) < 0) {
			throw new IllegalStateException("Withdrawal declined, insufficient funds!");
		}
		raiseEvent(FundsWithdrawnEvent.builder()
				.id(this.id)
				.amount(amount)
				.build());
	}
	
	public void apply(FundsWithdrawnEvent event) {
		this.id = event.getId();
		this.balance = this.balance.subtract(event.getAmount());
	}
	
	public void closeAccount() {
		if(!this.active) {
			throw new IllegalStateException("The account has alrady been closed");
		}
		raiseEvent(AccountClosedEvent.builder()
				.id(this.id)
				.build());
	}
	
	public void apply(AccountClosedEvent event) {
		this.id = event.getId();
		this.active = false;
	}
}
