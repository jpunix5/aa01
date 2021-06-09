package com.Board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.Board.entities.TableColumnEntity;
import com.Board.page.PageObject;
import com.Board.service.NativeSQLService;
import com.Board.service.TableColumnService;

@Controller
public class BoardController {

	@Autowired
	private TableColumnService tablecolumnservice;
	@Autowired
	private NativeSQLService nativesqlservice;
	
	/*
	 * 기본 CRUD 를 위한 Controller
	 * view로 값을 보낼 때는 Model object에 넣어서 보낸다
	 */
	@GetMapping("/")
	public String home(Model model) {
		
		return "thymeleaf/home";
	}
	
	@RequestMapping(value="/list.do", method=RequestMethod.GET)
	public String list(String tableName) {
		
		return "thymeleaf/board/MenuPage";
	}
	
	
	@RequestMapping(value="/viewtable", method = RequestMethod.GET)
	public ModelAndView viewTable(String table, Integer nowPageNo, Integer perPageCn) {
		
		ModelAndView tableData = new ModelAndView();
		tableData.setView(new MappingJackson2JsonView());
		
		//paging item
		PageObject pageObject = new PageObject();
		pageObject.setPerPageCn(perPageCn);
		Integer startPageNo = (nowPageNo - 1) * pageObject.getPerPageCn();
		pageObject.setStartPageNo(startPageNo);
		
		//table column info
		List<TableColumnEntity> tableinfo = tablecolumnservice.findByTable(table);
		tableData.addObject("tableinfo", tableinfo);
		
		//paging info
        int pageTotalCN = nativesqlservice.totalCount(table);
        tableData.addObject("totalCN", pageTotalCN);
        tableData.addObject("perPageCn", perPageCn);
        tableData.addObject("pageSize",pageObject.getPageSize()); //블록당 페이지 수
        
        //table row_list data
        List<Object[]> resultlist = nativesqlservice.selectAll(table, pageObject.getStartPageNo(), pageObject.getPerPageCn());
        tableData.addObject("users", resultlist);
        
        return tableData;
	}
	
	
	
	/*
	 * delete 기능의 공통화를 위한 controller
	 * delete 버튼을 눌렀을 때 해당 데이터를 삭제 한다
	 */
	@RequestMapping(value="/deleteTable", method = RequestMethod.GET)
	public void deleteTable(String table, String idx) {
		nativesqlservice.deleteSQL(table, idx);
	}
	
	
	
	@GetMapping(value="/MakeTable")
	public String MakeTable(Model model) {
		
		return "thymeleaf/MakeTable";
	}
	
	@RequestMapping(value="/tableList", method= RequestMethod.GET)
	public ModelAndView tableList() {
		ModelAndView tableListInfo = new ModelAndView();
		tableListInfo.setView(new MappingJackson2JsonView());
		
		List<Object[]> selectAlltable = nativesqlservice.selectAlltable();
		tableListInfo.addObject("masterTableInfo", selectAlltable);
		
		return tableListInfo;
	}
	
	@RequestMapping(value="/ajaxtable.do", method = RequestMethod.GET)
	   public ModelAndView ajaxtable(String table) {
	      
	      ModelAndView tablelist = new ModelAndView();
	      tablelist.setView(new MappingJackson2JsonView());
	      
	      List<TableColumnEntity> tableinfo = tablecolumnservice.findByTable(table);
	      tablelist.addObject("tableinfo", tableinfo);
	      
			/*
			 * if ("test_users".equals(table)) { List<TestUsersEntity> users =
			 * testusersservice.userlist(); tablelist.addObject("users", users); } else if
			 * ("test_board".equals(table)) { List<TestBoardEntity> boards =
			 * testboardservice.boardlist(); tablelist.addObject("users", boards); }
			 */
	      
	      return tablelist;
	   }
	
	
}