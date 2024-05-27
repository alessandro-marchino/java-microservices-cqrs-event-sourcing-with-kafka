package com.techbank.account.cmd.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techbank.account.cmd.api.commands.RestoreReadDBCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/restoreReadDB")
@Slf4j
public class RestoreReadDBController {

	private final CommandDispatcher commandDispatcher;

	@PostMapping
	public ResponseEntity<BaseResponse> openAccount() {
		try {
			commandDispatcher.send(new RestoreReadDBCommand());
			return new ResponseEntity<>(new BaseResponse("Read database restore request completed successfully"), HttpStatus.CREATED);
		} catch (IllegalStateException e) {
			log.warn("Client made a bad request - {}.", e.toString());
			return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			var safeErrorMessage = "Error while processing request to restore read database.";
			log.error("{}", safeErrorMessage, e);
			return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
