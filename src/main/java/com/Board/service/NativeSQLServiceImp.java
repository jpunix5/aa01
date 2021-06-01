package com.Board.service;

import java.util.HashMap;
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
        sql.append(" order by idx desc");
        sql.append(" limit ");
        sql.append(startPageNo);
        sql.append(" , ");
        sql.append(perPageCn);
        
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> resultlist = query.getResultList();
        
		return resultlist;
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
	
	public int totalCount(String table) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("select count(*) from ");
		sql.append(table);
		
		Query query = em.createNativeQuery(sql.toString());
        int resultlist = ((Number) query.getSingleResult()).intValue();
        
		return resultlist;
	}

	@Transactional
	@Modifying
	@Override
	public String insertSQL(HashMap<String, Object> insertValues) {
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlKey = new StringBuilder();
		StringBuilder sqlValues = new StringBuilder();
		String tableName = null;
		
		sql.append("insert into ");
        
		for(String key : insertValues.keySet()){
		    Object value = insertValues.get(key);
		    if("tableName" == key) {
		    	tableName = value.toString();
		    	sql.append(value);
		    	sql.append(" (");
		    }else if("idx" == key) {
		    	//idx 처리는 db에서 진행함.
		    }else {
		    	//insert sql문의 column 값을 작성 한다 
			    sqlKey.append(key);
			    sqlKey.append(",");
			    //insert sql문의 value 값을 작성 한다
			    sqlValues.append("'");
			    sqlValues.append(value);
			    sqlValues.append("',");
		    };
		};
		
		//insert문 작성을 위해 key문장 values문장 정리
		sqlKey.deleteCharAt((sqlKey.length()-1));
		sqlValues.deleteCharAt((sqlValues.length()-1));
		
		sql.append(sqlKey);
		sql.append(") values (");
		sql.append(sqlValues);
		sql.append(")");
		System.out.println(sql);
        
        Query query = em.createNativeQuery(sql.toString());
        query.executeUpdate();
        
        return tableName;
	}

}
