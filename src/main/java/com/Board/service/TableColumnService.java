package com.Board.service;

import java.util.List;

import com.Board.entities.TableColumnEntity;

public interface TableColumnService {
	List<TableColumnEntity> findByTable(String table);
}
