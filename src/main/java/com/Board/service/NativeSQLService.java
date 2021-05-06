package com.Board.service;

import java.util.List;
import java.util.Map;

public interface NativeSQLService {
	
	List<Object[]> selectAll(String table);
	
	void insertSQL(Map<String, Object> insertValue);

}
