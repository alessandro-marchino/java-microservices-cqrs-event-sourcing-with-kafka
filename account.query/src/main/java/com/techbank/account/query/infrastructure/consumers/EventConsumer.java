package com.techbank.account.query.infrastructure.consumers;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

import com.techbank.cqrs.core.events.BaseEvent;

public interface EventConsumer {
	void consume(@Payload BaseEvent event, Acknowledgment ack);
}
