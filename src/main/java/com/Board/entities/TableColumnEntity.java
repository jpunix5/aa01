package com.Board.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="table_column")
@Data
public class TableColumnEntity {
	@Id
	@Column(name="idx")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idx;
	
	@Column(name="table_name")
	private String tablename;
	
	@Column(name="column_name")
	private String columnname;
	
	@Column(name="column_type")
	private String columntype;

	public TableColumnEntity() {
		
	}
}
