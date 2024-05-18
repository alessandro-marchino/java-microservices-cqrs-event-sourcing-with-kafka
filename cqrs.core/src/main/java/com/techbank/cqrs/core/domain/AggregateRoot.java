package com.techbank.cqrs.core.domain;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.techbank.cqrs.core.events.BaseEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AggregateRoot {
	@Getter
	protected String id;
	@Getter
	@Setter
	private int version = -1;
	private final List<BaseEvent> changes = new ArrayList<>();

	public List<BaseEvent> getUncommitedChanges() {
		return this.changes;
	}
	public void makeChangesAsCommitted() {
		this.changes.clear();
	}
	protected void applyChange(BaseEvent event, boolean isNewEvent) {
		try {
			var method = getClass().getDeclaredMethod("apply", event.getClass());
			method.setAccessible(true);
			method.invoke(this, event);
		} catch (NoSuchMethodException e) {
			log.warn(MessageFormat.format("The apply method was not found in the aggregate for {0}", event.getClass().getName()));
		} catch(Exception e) {
			log.error("Error applying event to aggregate", e);
		} finally {
			if(isNewEvent) {
				this.changes.add(event);
			}
		}
	}
	public void raiseEvent(BaseEvent event) {
		applyChange(event, true);
	}
	public void replayEvent(Iterable<BaseEvent> events) {
		events.forEach(event -> this.applyChange(event, false));
	}
}
