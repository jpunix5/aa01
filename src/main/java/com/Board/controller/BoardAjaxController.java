package com.Board.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Board.entities.TestBoardEntity;
import com.Board.service.NativeSQLService;
import com.Board.service.TableColumnService;
import com.Board.service.TestBoardService;
import com.Board.service.TestUsersService;

/*
 * ajax post 전송 처리를 위한 @RestController
 * 
 */
@RestController
public class BoardAjaxController {
	
	@Autowired
	private NativeSQLService nativesqlservice;
	
	/*
	 * insert 처리를 위한 controller
	 */
	@RequestMapping(value="/ajaxInsert")
	public String ajaxinsert(@RequestBody HashMap<String, Object> insertValues) {

		return nativesqlservice.insertSQL(insertValues);
	}

}