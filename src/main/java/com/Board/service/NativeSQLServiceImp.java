package com.Board.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.exolab.castor.mapping.xml.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NativeSQLServiceImp implements NativeSQLService {
	@Autowired
	private EntityManager em;

	@Override
	public List<Object[]> selectAll(String table) {
		StringBuilder sql = new StringBuilder();
		        
        sql.append("select * from ");
        sql.append(table);
        
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> resultlist = query.getResultList();
        
		return resultlist;
	}
	
	@Override
	public void insertSQL(Map<String, Object> insertvalue) {
		
	}
	
	@Transactional
	@Modifying
	@Override
	public void deleteSQL(String table, String idx) {
		StringBuilder sql = new StringBuilder();
        
        sql.append("delete from ");
        sql.append(table);
        sql.append(" where idx = ");
        sql.append(idx);
        
        Query query = em.createNativeQuery(sql.toString());
        query.executeUpdate();
//        List<Object[]> resultlist = query.getResultList();
	}

}
