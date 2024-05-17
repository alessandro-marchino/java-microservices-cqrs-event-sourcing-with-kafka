package com.techbank.account.common.events;

import com.techbank.cqrs.core.events.BaseEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {

}
