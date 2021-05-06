package com.Board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.Board.service.NativeSQLService;
import com.Board.service.TableColumnService;
import com.Board.service.TestBoardService;
import com.Board.service.TestUsersService;
import com.Board.entities.TableColumnEntity;
import com.Board.entities.TestBoardEntity;
import com.Board.entities.TestUsersEntity;

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
	
	@RequestMapping(value="/list.do")
	public String list(@ModelAttribute TestUsersEntity board, Model model) {
		List<TestUsersEntity> users = testusersservice.userlist();
		model.addAttribute("users", users);
		
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
	public ModelAndView viewtable(String table) {
		
		ModelAndView tableData = new ModelAndView();
		tableData.setView(new MappingJackson2JsonView());
		
		//table column info
		List<TableColumnEntity> tableinfo = tablecolumnservice.findByTable(table);
		tableData.addObject("tableinfo", tableinfo);
		
		//table row_list data
        List<Object[]> resultlist = nativesqlservice.selectAll(table);
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

	@RequestMapping(value="/ajaxwrite.do", method = RequestMethod.GET)
	public void ajaxwrite(TestUsersEntity user) {
		testusersservice.usercreate(user);
	}
	
	@RequestMapping(value="/ajaxwriteboard.do", method = RequestMethod.GET)
	public void ajaxwriteboard(TestBoardEntity user) {
		testboardservice.boardcreate(user);
	}
	
	@RequestMapping(value="/ajaxInsert", method = RequestMethod.GET)
	public void ajaxinsert(Map<Object, Object> insertValue) {
//		nativesqlservice.insertSQL(insertValue);
		System.out.println(insertValue);
	}
	
}
