package com.techbank.account.cmd.infrastructure.mongodb;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class MongoConvertersConfig {

	@Bean
	MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(List.of(
			new MongoOffsetDateTimeReader(),
			new MongoOffsetDateTimeWriter()
		));
	}
}
