package com.pgbezerra.datamigration.step;


import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pgbezerra.datamigration.model.Person;

@Configuration
public class MigratePersonStepConfig {
	
	private StepBuilderFactory stepBuilderFactory;

	public MigratePersonStepConfig(StepBuilderFactory stepBuilderFactory) {
		this.stepBuilderFactory = stepBuilderFactory;
	}
	
	@Bean(name = "migrationPersonStep")
	public Step migrationPersonStep(
			@Qualifier(value = "personFileReader") ItemReader<Person> personFileReader,
			@Qualifier(value = "personDataWriter") ItemWriter<Person> personDataWriter) {
		return stepBuilderFactory
				.get("migrationpersonStep")
				.<Person, Person>chunk(100)
				.reader(personFileReader)
				.writer(persons -> persons.forEach(System.out::println))
				.build();
	}

}
