package com.Board.service;

import java.util.List;
import java.util.Map;

public interface NativeSQLService {
	
	List<Object[]> selectAll(String table, int startPageNo, int perPageCn);
	
	void insertSQL(Map<String, Object> insertValue);
	
	void deleteSQL(String table, String idx);
	
	int totalCount(String table);

}
