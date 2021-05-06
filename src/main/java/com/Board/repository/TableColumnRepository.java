package com.Board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Board.entities.TableColumnEntity;

public interface TableColumnRepository extends JpaRepository<TableColumnEntity, Integer>{
	List<TableColumnEntity> findByTablename(String table);
}
