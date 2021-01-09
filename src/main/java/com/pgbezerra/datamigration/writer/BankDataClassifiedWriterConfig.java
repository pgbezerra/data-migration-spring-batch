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

import com.pgbezerra.datamigration.model.BankData;

@Configuration
public class BankDataClassifiedWriterConfig {

	@Bean(name = "classifierBankDataItemWriter")
	public ClassifierCompositeItemWriter<BankData> classifierBankDataItemWriter(
			@Qualifier(value = "bankDataWriter") JdbcBatchItemWriter<BankData> personDataWriter,
			@Qualifier(value = "invalidBankDataWriter") FlatFileItemWriter<BankData> invalidPersonWriter) {
		return new ClassifierCompositeItemWriterBuilder<BankData>()
				.classifier(classifier(personDataWriter, invalidPersonWriter)).build();
	}

	private Classifier<BankData, ItemWriter<? super BankData>> classifier(
			JdbcBatchItemWriter<BankData> personDataWriter, FlatFileItemWriter<BankData> invalidPersonWriter) {
		return new Classifier<BankData, ItemWriter<? super BankData>>() {

			private static final long serialVersionUID = 1L;

			@Override
			public ItemWriter<? super BankData> classify(BankData bankData) {
				if (bankData.isValid())
					return personDataWriter;
				return invalidPersonWriter;
			}
		};
	}

}
