package com.pgbezerra.datamigration.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pgbezerra.datamigration.model.Person;

@Configuration
public class PersonClassifiedWriterConfig {
	
	@Bean(name = "classifierPersonItemWriter")
	public ClassifierCompositeItemWriter<Person> classifierPersonItemWriter(
			@Qualifier(value = "personDataWriter") JdbcBatchItemWriter<Person> personDataWriter,
			@Qualifier(value = "invalidPersonWriter") FlatFileItemWriter<Person> invalidPersonWriter){
		return new ClassifierCompositeItemWriterBuilder<Person>()
				.classifier(classifier(personDataWriter, invalidPersonWriter))
				.build();
	}

	private Classifier<Person, ItemWriter<? super Person>> classifier(JdbcBatchItemWriter<Person> personDataWriter,
			FlatFileItemWriter<Person> invalidPersonWriter) {
		return new Classifier<Person, ItemWriter<? super Person>>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public ItemWriter<? super Person> classify(Person person) {
				if(person.isValid())
					return personDataWriter;
				return invalidPersonWriter;
			}
		};
	}

}
