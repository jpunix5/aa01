package com.Board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface NativeSQLService {
	
	List<Object[]> selectAll(String table, int startPageNo, int perPageCn);
	
	String insertSQL(HashMap<String, Object> insertValues);
	
	void deleteSQL(String table, String idx);
	
	int totalCount(String table);

}
