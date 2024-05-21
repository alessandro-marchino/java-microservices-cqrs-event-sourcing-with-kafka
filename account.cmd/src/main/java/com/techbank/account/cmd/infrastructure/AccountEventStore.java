package com.techbank.account.cmd.infrastructure;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.account.cmd.domain.EventStoreRepository;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.events.EventModel;
import com.techbank.cqrs.core.exceptions.AggregateNotFoundException;
import com.techbank.cqrs.core.exceptions.ConcurrencyException;
import com.techbank.cqrs.core.infrastructure.EventStore;
import com.techbank.cqrs.core.producers.EventProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {
	private final EventStoreRepository eventStoreRepository;
	private final EventProducer eventProducer;

	@Override
	public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
		var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
		if(expectedVersion != -1 && eventStream.getLast().getVersion() != expectedVersion) {
			throw new ConcurrencyException();
		}
		var version = expectedVersion;
		for(var event : events) {
			version++;
			event.setVersion(version);
			var eventModel = EventModel.builder()
					.timestamp(OffsetDateTime.now())
					.aggregateIdentifier(aggregateId)
					.aggregateType(AccountAggregate.class.getTypeName())
					.version(version)
					.eventType(event.getClass().getTypeName())
					.eventData(event)
					.build();
			var persistedEvent = eventStoreRepository.save(eventModel);
			if(!persistedEvent.getId().isEmpty()) {
				this.eventProducer.produce(event.getClass().getSimpleName(), event);
			}
		}
	}

	@Override
	public List<BaseEvent> getEvents(String aggregateId) {
		var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
		if(eventStream == null || eventStream.isEmpty()) {
			throw new AggregateNotFoundException("Incorrect aggregate ID provided");
		}
		return eventStream.stream().map(x -> x.getEventData()).toList();
	}

}
