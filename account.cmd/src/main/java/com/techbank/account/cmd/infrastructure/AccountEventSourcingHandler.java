package com.techbank.account.cmd.infrastructure;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.cqrs.core.domain.AggregateRoot;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.handlers.EventSourcingHandler;
import com.techbank.cqrs.core.infrastructure.EventStore;
import com.techbank.cqrs.core.producers.EventProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
	private final EventStore eventStore;
	private final EventProducer eventProducer;
	@Value("${spring.kafka.topic}")
	private String topic;

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

	@Override
	public void republishEvents() {
		var aggregateIds = eventStore.getAggregateIds();
		for(var aggregateId : aggregateIds) {
			var aggregate = getById(aggregateId);
			if(aggregate == null || !aggregate.isActive()) {
				continue;
			}
			var events = eventStore.getEvents(aggregateId);
			for(var event : events) {
				eventProducer.produce(topic, event);
			}
		}
	}

}
