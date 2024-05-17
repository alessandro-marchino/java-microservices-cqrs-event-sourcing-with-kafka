package com.techbank.account.cmd.api.commands;

import java.math.BigDecimal;

import com.techbank.cqrs.core.commands.BaseCommand;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepositFundsCommand extends BaseCommand {
	private BigDecimal amount;
}
