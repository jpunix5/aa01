package com.Board.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.exolab.castor.mapping.xml.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
