package com.pgbezerra.datamigration.step;


import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
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
			@Qualifier(value = "classifierPersonItemWriter") ClassifierCompositeItemWriter<Person> classifierPersonItemWriter,
			@Qualifier(value = "invalidPersonWriter") FlatFileItemWriter<Person> invalidPersonWriter) {
		return stepBuilderFactory
				.get("migrationpersonStep")
				.<Person, Person>chunk(100)
				.reader(personFileReader)
				.writer(classifierPersonItemWriter)
				.stream(invalidPersonWriter)
				.build();
	}

}
