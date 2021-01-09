package com.pgbezerra.datamigration.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class DataMigrationJobConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(DataMigrationJobConfig.class);
	
	private JobBuilderFactory jobBuilderFactory;

	public DataMigrationJobConfig(JobBuilderFactory jobBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
	}
	
	@Bean
	public Job dataMigrationJob(
			@Qualifier(value = "migrationPersonStep") Step migratePersonStep,
			@Qualifier(value = "migrationBankDataStep") Step migrateBankDataStep) {
		LOG.info("Starting job config");
		return jobBuilderFactory
				.get("dataMigrationJob")
				.start(paralelSteps(migratePersonStep, migrateBankDataStep))
				.end()
				.incrementer(new RunIdIncrementer())
				.build();
	}

	private Flow paralelSteps(Step migratePersonStep, Step migrateBankDataStep) {
		LOG.info("Start paralel jobs");
		Flow migrateBankDataFlow = new FlowBuilder<Flow>("migrateBankDataStep")
				.start(migrateBankDataStep)
				.build();
		return new FlowBuilder<Flow>("paralelSteps")
				.start(migratePersonStep)
				.split(new SimpleAsyncTaskExecutor())
				.add(migrateBankDataFlow)
				.build();
	}
	

}
