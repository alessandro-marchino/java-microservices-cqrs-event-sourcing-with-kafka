package com.techbank.account.cmd.api.commands;

import org.springframework.stereotype.Service;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.cqrs.core.handlers.EventSourcingHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountCommandHandler implements CommandHandler {
	private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

	@Override
	public void handle(OpenAccountCommand command) {
		var aggregate = new AccountAggregate(command);
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(DepositFundsCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
		aggregate.depositFunds(command.getAmount());
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(WithdrawFundsCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
		aggregate.withdrawAmount(command.getAmount());
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(CloseAccountCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
		aggregate.closeAccount();
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(RestoreReadDBCommand command) {
		eventSourcingHandler.republishEvents();
	}
}
