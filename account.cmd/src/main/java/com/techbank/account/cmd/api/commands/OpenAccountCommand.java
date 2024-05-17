package com.techbank.account.cmd.api.commands;

import java.math.BigDecimal;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.commands.BaseCommand;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenAccountCommand extends BaseCommand {
	private String accountHolder;
	private AccountType accountType;
	private BigDecimal openingBalance;
}
