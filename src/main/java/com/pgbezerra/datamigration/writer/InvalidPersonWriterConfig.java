package com.pgbezerra.datamigration.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.pgbezerra.datamigration.model.Person;

@Component
public class InvalidPersonWriterConfig {
	
	@Bean(name = "invalidPersonWriter")
	public FlatFileItemWriter<Person> invalidPersonWriter(){
		return new FlatFileItemWriterBuilder<Person>()
				.name("invalidPersonWriter")
				.resource(new FileSystemResource("files/invalid-persons.txt"))
				.delimited()
				.names("id")
				.build();
	}

}
