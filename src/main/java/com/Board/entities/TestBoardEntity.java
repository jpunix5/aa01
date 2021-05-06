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
@Table(name="test_board")
@Data
public class TestBoardEntity {
	@Id
	@Column(name="idx")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idx;
	
	@Column(name="title")
	private String title;
	
	@Column(name="contant")
	private String contant;
	
	@Column(name="writer")
	private String writer;
	
	@Column(name="write_date")
	private String write_date;

	public TestBoardEntity() {
	
	}
}
