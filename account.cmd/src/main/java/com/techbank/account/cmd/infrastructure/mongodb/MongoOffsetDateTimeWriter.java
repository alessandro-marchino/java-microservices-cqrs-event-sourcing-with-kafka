package com.techbank.account.cmd.infrastructure.mongodb;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.core.convert.converter.Converter;

public class MongoOffsetDateTimeWriter implements Converter<OffsetDateTime, String> {

	@Override
	public String convert(OffsetDateTime source) {
		return source.toInstant().atZone(ZoneOffset.UTC).toString();
	}

}
