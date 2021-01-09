package com.pgbezerra.datamigration.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.pgbezerra.datamigration.annotations.Header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Header
	private String name;
	@Header
	private String email;
	@Header
	private LocalDate birthDate;
	@Header
	private Integer id;
	
	

}
