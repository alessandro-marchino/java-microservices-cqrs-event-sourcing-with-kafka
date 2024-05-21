package com.techbank.account.cmd.api.commands;

public interface CommandsHandler {

	void handle(OpenAccountCommand command);
	void handle(DepositFundsCommand command);
	void handle(WithdrawFundsCommand command);
	void handle(CloseAccountCommand command);
}
