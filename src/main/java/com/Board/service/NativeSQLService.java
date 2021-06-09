package com.Board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface NativeSQLService {
	
	List<Object[]> selectAll(String table, int startPageNo, int perPageCn);
	
	List<Object[]> selectAlltable();
	
	String insertSQL(HashMap<String, Object> insertValues);
	
	void deleteSQL(String table, String idx);
	
	int totalCount(String table);

	String updateSQL(HashMap<String, Object> updateValues);

	String MasterTableAddSQL(HashMap<String, Object> tableInfo);

	String createTableSQL(HashMap<String, Object> tableInfo);
	
	void dropThisTable(String tableName);
	void deleteInMaster(String tableName);

}
