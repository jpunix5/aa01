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
	public List<Object[]> selectAll(String table, int startPageNo, int perPageCn) {
		StringBuilder sql = new StringBuilder();
		        
        sql.append("select * from ");
        sql.append(table);
        sql.append(" limit ");
        sql.append(startPageNo);
        sql.append(" , ");
        sql.append(perPageCn);
        
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> resultlist = query.getResultList();
        
		return resultlist;
	}
	
	@Override
	public void insertSQL(Map<String, Object> insertvalue) {
		
	}
	
	//transactional 기본은 rollback 인걸로 알고 있으나 commit 됨.
	//예상은 db auto commit 때문인가?
	@Transactional
	//modifying - update delete를 수행 하기 위한 @
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
