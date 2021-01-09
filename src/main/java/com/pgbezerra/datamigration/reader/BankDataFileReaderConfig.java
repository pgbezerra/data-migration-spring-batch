package com.pgbezerra.datamigration.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.pgbezerra.datamigration.annotations.processor.HeaderAnnotationProcessor;
import com.pgbezerra.datamigration.model.BankData;

@Configuration
public class BankDataFileReaderConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(BankDataFileReaderConfig.class);
	private static final String DELIMITER = ",";
	
	@Bean(name = "bankDataFileReader")
	public FlatFileItemReader<BankData> bankDataFileReader(){
		LOG.info("Reading file of bank data");
		return new FlatFileItemReaderBuilder<BankData>()
				.name("bankDataFileReader")
				.resource(new FileSystemResource("files/dados_bancarios.csv"))
				.delimited()
				.delimiter(DELIMITER)
				.names(HeaderAnnotationProcessor.extractHeaderNames(BankData.class))
				.addComment("--")
				.targetType(BankData.class)
				.build();
				
	}


}
