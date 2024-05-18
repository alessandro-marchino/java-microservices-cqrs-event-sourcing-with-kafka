package com.techbank.account.cmd.infrastructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techbank.cqrs.core.commands.BaseCommand;
import com.techbank.cqrs.core.commands.CommandHandlerMethod;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
	@SuppressWarnings("rawtypes")
	private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> ROUTES = new HashMap<>();

	@Override
	public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
		var handlers = ROUTES.computeIfAbsent(type, e -> new LinkedList<>());
		handlers.add(handler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void send(BaseCommand command) {
		var handlers = ROUTES.get(command.getClass());
		if(handlers == null || handlers.isEmpty()) {
			throw new RuntimeException("No command handler are registered");
		}
		if(handlers.size() > 1) {
			throw new RuntimeException("Cannot send command to more than one handler");
		}
		handlers.get(0).handle(command);
	}

}
