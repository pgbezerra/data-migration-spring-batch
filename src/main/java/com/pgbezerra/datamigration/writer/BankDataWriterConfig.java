package com.pgbezerra.datamigration.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.pgbezerra.datamigration.model.BankData;

@Component
public class BankDataWriterConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(BankDataWriterConfig.class);
	
	
	@Bean(name = "bankDataWriter")
	public JdbcBatchItemWriter<BankData> bankDataWriter(@Qualifier("appDataSource") DataSource dataSource){
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO tb_bank_data ");
		sql.append("   (bank_data_id, person_id, branch, account, bank) ");
		sql.append(" VALUES ");
		sql.append("   (?, ?, ?, ?, ?)");
		LOG.info("Building jdbc bank data writer");
		return new JdbcBatchItemWriterBuilder<BankData>()
				.dataSource(dataSource)
				.sql(sql.toString())
				.itemPreparedStatementSetter(itemPreparedStatementSetter())
				.build();
	}

	private ItemPreparedStatementSetter<BankData> itemPreparedStatementSetter() {
		return new ItemPreparedStatementSetter<BankData>() {

			@Override
			public void setValues(BankData bankData, PreparedStatement ps) throws SQLException {
				ps.setInt(1, bankData.getId());
				ps.setInt(2, bankData.getPersonId());
				ps.setInt(3, bankData.getBranch());
				ps.setInt(4, bankData.getAccount());
				ps.setInt(5, bankData.getBank());
			}
			
		};
	}

}
