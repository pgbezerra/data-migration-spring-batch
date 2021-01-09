package com.pgbezerra.datamigration.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindException;

import com.pgbezerra.datamigration.annotations.processor.HeaderAnnotationProcessor;
import com.pgbezerra.datamigration.model.Person;

@Configuration
public class PersonFileReaderConfig {

	private static final Logger LOG = LoggerFactory.getLogger(PersonFileReaderConfig.class);

	private static final String DELIMITER = ",";

	@Bean(name = "personFileReader")
	public FlatFileItemReader<Person> personFileReader() {
		LOG.info("Reading file of person");
		return new FlatFileItemReaderBuilder<Person>().name("personFileReader")
				.resource(new FileSystemResource("files/pessoas.csv")).delimited().delimiter(DELIMITER)
				.names(HeaderAnnotationProcessor.extractHeaderNames(Person.class)).addComment("--")
				.fieldSetMapper(fieldSetMapper()).build();

	}

	private FieldSetMapper<Person> fieldSetMapper() {
		return new FieldSetMapper<Person>() {

			@Override
			public Person mapFieldSet(FieldSet fieldSet) throws BindException {
				Person bankData = new Person();
				bankData.setName(fieldSet.readString("name"));
				bankData.setEmail(fieldSet.readString("email"));
				try {
					bankData.setBirthDate(
							new java.sql.Date(fieldSet.readDate("birthDate", "yyyy-MM-dd hh:mm").getTime())
									.toLocalDate());
				} catch (Exception e) {
					LOG.error("Error on convert field birthDate", e);
				}
				bankData.setId(fieldSet.readInt("id"));
				return bankData;
			}
		};
	}

}
