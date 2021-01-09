package com.pgbezerra.datamigration.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.pgbezerra.datamigration.model.Person;

@Component
public class PersonDataWriterConfig {
	
	@Bean(name = "personDataWriter")
	public JdbcBatchItemWriter<Person> personDataWriter(@Qualifier(value = "appDataSource") DataSource dataSource){
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO tb_person ");
		sql.append("   (person_id, name, email, birthdate) ");
		sql.append(" VALUES ");
		sql.append("   (?, ?, ?, ?)");
		return new JdbcBatchItemWriterBuilder<Person>()
				.dataSource(dataSource)
				.sql(sql.toString())
				.itemPreparedStatementSetter(itemPreparedStatementSetter())
				.build();
	}

	private ItemPreparedStatementSetter<Person> itemPreparedStatementSetter() {
		return new ItemPreparedStatementSetter<Person>() {

			@Override
			public void setValues(Person person, PreparedStatement ps) throws SQLException {
				ps.setInt(1, person.getId());
				ps.setString(2, person.getName());
				ps.setString(3, person.getEmail());
				ps.setObject(4, person.getBirthDate());
			}
			
		};
	}

}
