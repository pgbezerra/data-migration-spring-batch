package com.pgbezerra.datamigration.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.pgbezerra.datamigration.model.BankData;

@Component
public class InvalidDataBankWriterConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(InvalidDataBankWriterConfig.class);

	
	@Bean(name = "invalidBankDataWriter")
	public FlatFileItemWriter<BankData> invalidBankDataWriter(){
		LOG.info("Creating invalid bank data writer");
		return new FlatFileItemWriterBuilder<BankData>()
				.name("invalidBankDataWriter")
				.resource(new FileSystemResource("files/invalid-persons.txt"))
				.delimited()
				.names("id")
				.build();
	}

}
