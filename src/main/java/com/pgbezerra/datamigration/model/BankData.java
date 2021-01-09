package com.pgbezerra.datamigration.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer personId;
	private Integer branch;
	private Integer account;
	private Integer bank;
	
}
