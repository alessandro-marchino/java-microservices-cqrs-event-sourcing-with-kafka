package com.techbank.account.cmd.infrastructure.mongodb;

import java.time.OffsetDateTime;

import org.springframework.core.convert.converter.Converter;

public class MongoOffsetDateTimeReader implements Converter<String, OffsetDateTime> {

	@Override
	public OffsetDateTime convert(String source) {
		return OffsetDateTime.parse(source);
	}

}
