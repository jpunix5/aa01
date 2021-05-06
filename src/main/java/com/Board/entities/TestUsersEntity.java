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
@Table(name="test_users")
@Data
public class TestUsersEntity {
	@Id
	@Column(name="idx")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//DB에서 사용하는 AUTO_INCREMENT 를 사용 하여 값 할당
	private Integer idx;
	
	@Column(name="user_id")
	private String user_id;
	
	@Column(name="user_pw")
	private String user_pw;
	
	@Column(name="user_name")
	private String user_name;
	
	@Column(name="user_email")
	private String user_email;
	
	@Column(name="user_create_date")
	private String user_create_date;
	
	@Column(name="user_login_date")
	private String user_login_date;

	public TestUsersEntity() {
	
	}
}
