package com.pgbezerra.datamigration.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pgbezerra.datamigration.model.BankData;

@Configuration
public class MigrateBankDataStepConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(MigrateBankDataStepConfig.class);


	private StepBuilderFactory stepBuilderFactory;

	public MigrateBankDataStepConfig(StepBuilderFactory stepBuilderFactory) {
		this.stepBuilderFactory = stepBuilderFactory;
	}
	
	@Bean(name = "migrationBankDataStep")
	public Step migrationBankDataStep(
			@Qualifier(value = "bankDataFileReader") ItemReader<BankData> bankDataFileReader,
			@Qualifier(value = "bankDataWriter") ItemWriter<BankData> bankDataWriter) {
		LOG.info("Build bank data migration step");
		return stepBuilderFactory
				.get("migrationBankDataStep")
				.<BankData, BankData>chunk(100)
				.reader(bankDataFileReader)
				.writer(bankDataWriter)
				.build();
	}
	
}
