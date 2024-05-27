package com.techbank.cqrs.core.events;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "eventStore")
public class EventModel {
	@Id
	private String id;
	private OffsetDateTime timestamp;
	private String aggregateIdentifier;
	private String aggregateType;
	private int version;
	private String eventType;
	private BaseEvent eventData;
}
