package com.techbank.account.query.api.controller;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techbank.account.query.api.dto.AccountLookupResponse;
import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.api.query.FindAccountByHolderQuery;
import com.techbank.account.query.api.query.FindAccountByIdQuery;
import com.techbank.account.query.api.query.FindAccountsWithBalanceQuery;
import com.techbank.account.query.api.query.FindAllAccountsQuery;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/bankAccountLookup")
@RequiredArgsConstructor
@Slf4j
public class AccountLookupController {

	private final QueryDispatcher queryDispatcher;

	@GetMapping
	public ResponseEntity<AccountLookupResponse> getAllAccounts() {
		try {
			List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());
			if(accounts == null || accounts.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(AccountLookupResponse.builder()
					.accounts(accounts)
					.message(MessageFormat.format("Successfully returned {0} bank account(s)", accounts.size()))
					.build(), HttpStatus.OK);
		} catch(Exception e) {
			var safeErrorMsg = "Failed to complete get all accounts request";
			log.error(safeErrorMsg, e);
			return new ResponseEntity<>(new AccountLookupResponse(safeErrorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/byId/{id}")
	public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable("id") String id) {
		try {
			List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
			if(accounts == null || accounts.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(AccountLookupResponse.builder()
					.accounts(accounts)
					.message("Successfully returned bank account")
					.build(), HttpStatus.OK);
		} catch(Exception e) {
			var safeErrorMsg = "Failed to complete get account by id request";
			log.error(safeErrorMsg, e);
			return new ResponseEntity<>(new AccountLookupResponse(safeErrorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/byHolder/{holder}")
	public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable("holder") String holder) {
		try {
			List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(holder));
			if(accounts == null || accounts.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(AccountLookupResponse.builder()
					.accounts(accounts)
					.message(MessageFormat.format("Successfully returned bank account", accounts.size()))
					.build(), HttpStatus.OK);
		} catch(Exception e) {
			var safeErrorMsg = "Failed to complete get account by holder request";
			log.error(safeErrorMsg, e);
			return new ResponseEntity<>(new AccountLookupResponse(safeErrorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/withBalance/{equalityType}/{balance}")
	public ResponseEntity<AccountLookupResponse> getAccountsWithBalance(@PathVariable("equalityType") EqualityType equalityType, @PathVariable("balance") BigDecimal balance) {
		try {
			List<BankAccount> accounts = queryDispatcher.send(new FindAccountsWithBalanceQuery(equalityType, balance));
			if(accounts == null || accounts.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(AccountLookupResponse.builder()
					.accounts(accounts)
					.message(MessageFormat.format("Successfully returned {0} bank account(s)", accounts.size()))
					.build(), HttpStatus.OK);
		} catch(Exception e) {
			var safeErrorMsg = "Failed to complete get accounts with balance request";
			log.error(safeErrorMsg, e);
			return new ResponseEntity<>(new AccountLookupResponse(safeErrorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
