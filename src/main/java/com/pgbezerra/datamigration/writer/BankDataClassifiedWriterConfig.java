package com.pgbezerra.datamigration.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pgbezerra.datamigration.model.BankData;

@Configuration
public class BankDataClassifiedWriterConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(BankDataClassifiedWriterConfig.class);


	@Bean(name = "classifierBankDataItemWriter")
	public ClassifierCompositeItemWriter<BankData> classifierBankDataItemWriter(
			@Qualifier(value = "bankDataWriter") JdbcBatchItemWriter<BankData> personDataWriter,
			@Qualifier(value = "invalidBankDataWriter") FlatFileItemWriter<BankData> invalidPersonWriter) {
		LOG.info("Build classified bank data item writer");
		return new ClassifierCompositeItemWriterBuilder<BankData>()
				.classifier(classifier(personDataWriter, invalidPersonWriter)).build();
	}

	private Classifier<BankData, ItemWriter<? super BankData>> classifier(
			JdbcBatchItemWriter<BankData> personDataWriter, FlatFileItemWriter<BankData> invalidPersonWriter) {
		return new Classifier<BankData, ItemWriter<? super BankData>>() {

			private static final long serialVersionUID = 1L;

			@Override
			public ItemWriter<? super BankData> classify(BankData bankData) {
				LOG.info("Checking the writter");
				if (bankData.isValid()) {
					LOG.info("Person is valid, returning jdbc writer");
					return personDataWriter;
				}
				LOG.warn("Person is invalid, returning invalid person writer");
				return invalidPersonWriter;
			}
		};
	}

}
