package com.techbank.account.cmd.infrastructure;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.producers.EventProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountEventProducer implements EventProducer {
	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void produce(String topicName, BaseEvent event) {
		this.kafkaTemplate.send(topicName, event);
	}

}
