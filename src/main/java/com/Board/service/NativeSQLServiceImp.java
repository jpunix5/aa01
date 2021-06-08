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
	
	@Transactional
	@Modifying
	@Override
	public String updateSQL(HashMap<String, Object> updateValues) {
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlValues = new StringBuilder();
		StringBuilder sqlidx = new StringBuilder();
		String tableName = null;
		
		sql.append("update ");
		
		for(String key : updateValues.keySet()){
			Object value = updateValues.get(key);
		    if("tableName" == key) {
		    	tableName = value.toString();
		    	sql.append(value);
		    	sql.append(" set ");
		    }else if("idx" == key) {
		    	sqlidx.append(value);
		    }else {
		    	sqlValues.append(key);
		    	sqlValues.append("='");
		    	sqlValues.append(value);
		    	sqlValues.append("',");
		    }
		};
		sqlValues.deleteCharAt((sqlValues.length()-1));
		sql.append(sqlValues);
		sql.append(" where idx=");
		sql.append(sqlidx);
		System.out.println(sql);
		
        Query query = em.createNativeQuery(sql.toString());
        query.executeUpdate();
		
		return tableName;
	}

	@Override
	   public List<Object[]> selectAlltable(){
	      StringBuilder sql = new StringBuilder();
	      sql.append("select distinct table_name from table_column");
	      
	      Query query = em.createNativeQuery(sql.toString());
	      List<Object[]> tablelist = query.getResultList();
	      
	      
	      return tablelist;
	   }
	   
	   
	   
	   @Transactional
		@Override
		public String createTableSQL(HashMap<String, Object> tableInfo) {
			StringBuilder sql = new StringBuilder();
			StringBuilder sqlKey = new StringBuilder();
			StringBuilder sqlValue = new StringBuilder();
			String tableName = null;
			
			sql.append("create table ");
			
			for(String key : tableInfo.keySet()){
				Object value = tableInfo.get(key);
			    if("tableName" == key) {
			    	tableName = value.toString();
			    	sql.append(value);
			    	sql.append(" ( `idx` int NOT NULL AUTO_INCREMENT,");
			    }else {
			    	sqlKey.append(key);
			    	sqlKey.deleteCharAt((sqlKey.length()-1));
			    	String StKey = sqlKey.toString();
			    	
			    	if("columnName".equals(StKey)) {
			    		sqlValue.append(value);
			    		sqlValue.append(" ");
			    		
			    		for(String chKey : tableInfo.keySet()){
			    			Object targetValue = tableInfo.get(chKey);
			    			
			    			if(!(key==chKey)) {
			    				if(key.charAt(key.length()-1)==chKey.charAt(chKey.length()-1)) {
					    			sqlValue.append(targetValue);
					    			sqlValue.append(",");
					    		}
			    			}
				    	}
			    	}
			    	sqlKey.delete(0, sqlKey.length());
			    }
			};
			
			sql.append(sqlValue);
			sql.append(" primary key(`idx`))");
			
			System.out.println(sql);
	        Query query = em.createNativeQuery(sql.toString());
	        query.executeUpdate();
	        
			return "tableName";
		}
		
		
		@Transactional
		@Modifying
		@Override
		public String MasterTableAddSQL(HashMap<String, Object> tableInfo) {
			StringBuilder sql = new StringBuilder();
			StringBuilder sqlKey = new StringBuilder();
			String tableName = null;
			
			for(String key : tableInfo.keySet()){
				Object value = tableInfo.get(key);
			    if("tableName" == key) {
			    	tableName = value.toString();
			    	break;
			    }
			}
			
			sql.append("insert into table_column(table_name, column_name, column_type) values('");
			sql.append(tableName);
			sql.append("'");
			sql.append(",");
			sql.append("'idx','text')");
			Query query2 = em.createNativeQuery(sql.toString());
	        query2.executeUpdate();
			
			for(String key : tableInfo.keySet()){
				sql.append("insert into table_column(table_name, column_name, column_type) values(");
				sql.append("'");
				sql.append(tableName);
				sql.append("'");
				sql.append(",");
				
				Object value = tableInfo.get(key);
				
				sqlKey.append(key);
		    	sqlKey.deleteCharAt((sqlKey.length()-1));
		    	String StKey = sqlKey.toString();
				
			    if("tableNam".equals(StKey)) {
			    	
			    }else if("columnName".equals(StKey)){
			    	sql.append("'");
			    	sql.append(value);
			    	sql.append("'");
			    	sql.append(",");
			    	for(String chKey : tableInfo.keySet()){
		    			Object targetValue = tableInfo.get(chKey);
		    			
		    			if(!(key==chKey)) {
		    				if(key.charAt(key.length()-1)==chKey.charAt(chKey.length()-1)) {
		    					sql.append("'");
				    			sql.append(targetValue); //vhar -> text로 수정
				    			sql.append("'");
				    			sql.append(")");
				    			System.out.println(sql);
				    			Query query = em.createNativeQuery(sql.toString());
				    	        query.executeUpdate();
				    		}
		    			}
			    	}
			    	
			    }
			    sqlKey.delete(0, sqlKey.length());
			    sql.delete(0, sql.length());
			};
			
			return "a";
		}
		
		@Transactional
		@Override
		public void dropThisTable(String tableName) {
			StringBuilder sql = new StringBuilder();
			
			sql.append("drop table ");
			sql.append(tableName);
			
			Query query = em.createNativeQuery(sql.toString());
			query.executeUpdate();
		}

		@Transactional
		@Override
		public void deleteInMaster(String tableName) {
			StringBuilder sql = new StringBuilder();
			
			sql.append("delete from table_column");
			sql.append(" where table_name = ");
			sql.append("'");
			sql.append(tableName);
			sql.append("'");
			
			Query query = em.createNativeQuery(sql.toString());
			query.executeUpdate();
		}
	   
	   
	   

}