package com.techbank.account.cmd.api.controllers;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techbank.account.cmd.api.commands.DepositFundsCommand;
import com.techbank.account.cmd.api.dto.OpenAccountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/depositFunds")
@Slf4j
public class DepositFundsController {

	private final CommandDispatcher commandDispatcher;

	@PutMapping("/{id}")
	public ResponseEntity<BaseResponse> depositFunds(@PathVariable("id") String id, @RequestBody DepositFundsCommand command) {
		command.setId(id);
		try {
			commandDispatcher.send(command);
			return new ResponseEntity<>(new BaseResponse("Deposit funds request completed successfully"), HttpStatus.OK);
		} catch (IllegalStateException e) {
			log.warn("Client made a bad request - {}.", e.toString());
			return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			var safeErrorMessage = MessageFormat.format("Error while processing request to deposit funds to bank account with id - {0}.", id);
			log.error("{}", safeErrorMessage, e);
			return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
