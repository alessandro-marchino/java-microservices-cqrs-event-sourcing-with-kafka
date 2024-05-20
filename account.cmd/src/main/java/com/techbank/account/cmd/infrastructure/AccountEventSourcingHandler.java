package com.techbank.account.cmd.infrastructure;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.cqrs.core.domain.AggregateRoot;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.handlers.EventSourcingHandler;
import com.techbank.cqrs.core.infrastructure.EventStore;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
	private final EventStore eventStore;

	@Override
	public void save(AggregateRoot aggregate) {
		eventStore.saveEvents(aggregate.getId(), aggregate.getUncommitedChanges(), aggregate.getVersion());
		aggregate.markChangesAsCommitted();
	}

	@Override
	public AccountAggregate getById(String id) {
		var aggregate = new AccountAggregate();
		var events = eventStore.getEvents(id);
		if(events != null && !events.isEmpty()) {
			aggregate.replayEvent(events);
			var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
			aggregate.setVersion(latestVersion.get());
		}
		return aggregate;
	}

}
