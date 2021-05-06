package com.Board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Board.entities.TestBoardEntity;
import com.Board.repository.TestBoardRepository;

@Service
public class TestBoardServiceImp implements TestBoardService {

	@Autowired
	private TestBoardRepository testboardrepository;
	
	@Override
	public List<TestBoardEntity> boardlist() {
		List<TestBoardEntity> boards = testboardrepository.findAll();
		return boards;
	}

	@Override
	public void boardcreate(TestBoardEntity board) {
		testboardrepository.save(board);
	}

	@Override
	public void boarddelete(Integer idx) {
		testboardrepository.deleteById(idx);
	}

	@Override
	public TestBoardEntity selectone(Integer idx) {
		return testboardrepository.getOne(idx);
	}

	@Override
	public void boardupdate(TestBoardEntity board) {
		TestBoardEntity testboard = testboardrepository.getOne(board.getIdx());
		
		testboard.setIdx(board.getIdx());
		testboard.setTitle(board.getTitle());
		testboard.setContant(board.getContant());
		testboard.setWrite_date(board.getWrite_date());
		
		testboardrepository.save(testboard);

	}

}
