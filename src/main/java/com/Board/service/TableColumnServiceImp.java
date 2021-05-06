package com.Board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Board.entities.TableColumnEntity;
import com.Board.repository.TableColumnRepository;

@Service
public class TableColumnServiceImp implements TableColumnService {

	@Autowired
	private TableColumnRepository tablecolumnrepository;
	
	@Override
	public List<TableColumnEntity> findByTable(String table) {

		return tablecolumnrepository.findByTablename(table);
	}

}
