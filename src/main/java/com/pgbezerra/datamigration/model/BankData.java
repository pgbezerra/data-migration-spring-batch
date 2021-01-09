package com.pgbezerra.datamigration.model;

import java.io.Serializable;
import java.util.Objects;

import com.pgbezerra.datamigration.annotations.Header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Header
	private Integer personId;
	@Header
	private Integer branch;
	@Header
	private Integer account;
	@Header
	private Integer bank;
	@Header
	private Integer id;
	
	public boolean isValid() {
		return Objects.nonNull(personId) && Objects.nonNull(branch) && Objects.nonNull(account) && Objects.nonNull(bank);
	}
	
}
