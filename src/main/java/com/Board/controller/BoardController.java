package com.Board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.Board.entities.TableColumnEntity;
import com.Board.entities.TestBoardEntity;
import com.Board.entities.TestUsersEntity;
import com.Board.page.PageObject;
import com.Board.service.NativeSQLService;
import com.Board.service.TableColumnService;
import com.Board.service.TestBoardService;
import com.Board.service.TestUsersService;

@Controller
public class BoardController {

	@Autowired
	private TestUsersService testusersservice;
	@Autowired
	private TestBoardService testboardservice;
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
	
	@RequestMapping(value="/write.do", method = RequestMethod.POST)
	public String write(TestUsersEntity board, Model model) {
		testusersservice.usercreate(board);
		
		return "redirect:/list.do";
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.GET)
	public String delete(Integer idx) {
		testusersservice.userdelete(idx);
		
		return "redirect:/list.do";
	}
	
	@RequestMapping(value="/read.do", method = RequestMethod.GET)
	public void selectOne(Model model, Integer idx) {
		model.addAttribute("user", testusersservice.selectone(idx));
		
	}
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	public String update(TestUsersEntity board, Model model) {
		System.out.println("test msg");
		
		testusersservice.usercreate(board);
		
		return "redirect:/list.do";
	}
	
	/*
	 * ajax 처리를 위한 controller
	 * @ResponseBody String 형태의 단순 문자 반환
	 * ModelAndView json에 원하는 데이터 타입을 넣어 반환 하기 위한 객체
	 * view로 부터 값을 받을 때는 (@ReqeustBody 타입 변수명) 을 이용 한다
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxlist", method = RequestMethod.GET)
	public List<TestUsersEntity> ajaxlist() {
		List<TestUsersEntity> users = testusersservice.userlist();
		return users;
	}
	
	@RequestMapping(value="/ajaxtable.do", method = RequestMethod.GET)
	public ModelAndView ajaxtable(String table) {
		
		ModelAndView tablelist = new ModelAndView();
		tablelist.setView(new MappingJackson2JsonView());
		
		List<TableColumnEntity> tableinfo = tablecolumnservice.findByTable(table);
		tablelist.addObject("tableinfo", tableinfo);
		
		if ("test_users".equals(table)) {
			List<TestUsersEntity> users = testusersservice.userlist();
			tablelist.addObject("users", users);
		} else if ("test_board".equals(table)) {
			List<TestBoardEntity> boards = testboardservice.boardlist();
			tablelist.addObject("users", boards);
		}
		
		return tablelist;
	}
	/*
	 * select all 기능의 공통화를 위한 controller
	 * view에 table을 그려 주기 위한 header(column)정보
	 * body(row list)값을 return 한다
	 */
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
	
	@RequestMapping(value="/ajaxdelete.do", method = RequestMethod.GET)
	public String ajaxdelete(Integer idx) {
		testusersservice.userdelete(idx);
		
		return "redirect:/list.do";
	}
	
	@RequestMapping(value="/ajaxdeleteboard.do", method = RequestMethod.GET)
	public void ajaxdeleteboard(Integer idx) {
		testboardservice.boarddelete(idx);
	}

	@RequestMapping(value="/ajaxwrite.do", method = RequestMethod.POST)
	public String ajaxwrite(@RequestBody TestUsersEntity user) {
		testusersservice.usercreate(user);
		return "bbb";
	}
	
	@RequestMapping(value="/ajaxwriteboard.do", method = RequestMethod.GET)
	public String ajaxwriteboard(TestBoardEntity user) {
		testboardservice.boardcreate(user);
		return "aaa";
	}
	
	
/*-----------------------------------------------------------------------
 * 해당 insert 기능은 @RestController 에서 처리 하기 위해 BoardAjaxController에서 처리
 * 
	@RequestMapping(value="/ajaxInsert")
//	public void ajaxinsert(HttpServletRequest model) {
	public void ajaxinsert(HashMap<String, String> model) {
//	public void ajaxinsert(@RequestBody TestBoardEntity model) {
//		nativesqlservice.insertSQL(insertValue);
		System.out.println("111");
		System.out.println(model);
		System.out.println("222");
//		System.out.println(write_date);
	}
-----------------------------------------------------------------------*/	
	
	
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
	
	
}