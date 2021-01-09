package com.pgbezerra.datamigration.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.pgbezerra.datamigration.model.BankData;

@Component
public class InvalidDataBankWriterConfig {
	
	@Bean(name = "invalidBankDataWriter")
	public FlatFileItemWriter<BankData> invalidBankDataWriter(){
		return new FlatFileItemWriterBuilder<BankData>()
				.name("invalidBankDataWriter")
				.resource(new FileSystemResource("files/invalid-persons.txt"))
				.delimited()
				.names("id")
				.build();
	}

}
