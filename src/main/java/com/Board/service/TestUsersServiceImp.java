package com.Board.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Board.entities.TestUsersEntity;
import com.Board.repository.TestUsersRepository;

@Service
public class TestUsersServiceImp implements TestUsersService {

	@Autowired
	private TestUsersRepository testusersrepository;

	@Override
	public List<TestUsersEntity> userlist() {
		List<TestUsersEntity> users = testusersrepository.findAll();
		return users;
	}

	@Override
	public void usercreate(TestUsersEntity user) {
		testusersrepository.save(user);
	}

	@Override
	public void userdelete(Integer idx) {
		testusersrepository.deleteById(idx);

	}

	@Override
	public TestUsersEntity selectone(Integer idx) {
		return testusersrepository.getOne(idx);
	}
	
	@Override
	public void userupdate(TestUsersEntity user) {
		TestUsersEntity userEntity = testusersrepository.getOne(user.getIdx());
		userEntity.setUser_pw(user.getUser_pw());
		userEntity.setUser_name(user.getUser_name());
		userEntity.setUser_email(user.getUser_email());
		userEntity.setUser_create_date(user.getUser_create_date());
		userEntity.setUser_login_date(user.getUser_login_date());
		
		testusersrepository.save(userEntity);
	}

}
