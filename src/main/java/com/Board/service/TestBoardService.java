package com.Board.service;

import java.util.List;

import com.Board.entities.TestBoardEntity;

public interface TestBoardService {
	List<TestBoardEntity> boardlist();
	
	void boardcreate(TestBoardEntity board);
	
	void boarddelete(Integer idx);
	
	TestBoardEntity selectone(Integer idx);
	
	void boardupdate(TestBoardEntity board);
}
