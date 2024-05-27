package com.techbank.account.query.infrastructure.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.techbank.account.query.infrastructure.handlers.EventHandler;
import com.techbank.cqrs.core.events.BaseEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountEventConsumer implements EventConsumer {
	private final EventHandler eventHandler;

	@Override
	@KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void consume(BaseEvent event, Acknowledgment ack) {
		try {
			var eventHandlerMethod = eventHandler.getClass().getDeclaredMethod("on", event.getClass());
			eventHandlerMethod.setAccessible(true);
			eventHandlerMethod.invoke(eventHandler, event);
			ack.acknowledge();
		} catch (Exception e) {
			throw new RuntimeException("Error while consuming event", e);
		}
	}

}
