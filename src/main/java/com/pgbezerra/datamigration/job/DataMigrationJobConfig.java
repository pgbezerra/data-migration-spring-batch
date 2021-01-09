package com.pgbezerra.datamigration.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class DataMigrationJobConfig {
	
	private JobBuilderFactory jobBuilderFactory;

	public DataMigrationJobConfig(JobBuilderFactory jobBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
	}
	
	@Bean
	public Job dataMigrationJob(
			@Qualifier(value = "migrationPersonStep") Step migratePersonStep,
			@Qualifier(value = "migrationBankDataStep") Step migrateBankDataStep) {
		return jobBuilderFactory
				.get("dataMigrationJob")
				.start(migratePersonStep)
				.next(migrateBankDataStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
	

}
