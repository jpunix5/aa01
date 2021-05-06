package com.Board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Board.entities.TestUsersEntity;

public interface TestUsersRepository extends JpaRepository<TestUsersEntity, Integer>{

}
