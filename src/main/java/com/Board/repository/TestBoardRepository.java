package com.Board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Board.entities.TestBoardEntity;

public interface TestBoardRepository extends JpaRepository<TestBoardEntity, Integer>{

}
