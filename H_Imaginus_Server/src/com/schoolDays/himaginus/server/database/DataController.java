package com.schoolDays.himaginus.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DataController {

	/**
	 * # ResultSet이 없는 단순 쿼리 : insert, update, delete
	 * @param sql(String) : DB에 실행 될 쿼리문
	 * @param params(Object[]) : ?에 매칭 될 데이터
	 * @return boolean : 쿼리 실행의 결과
	 */
	public boolean simpleQuery(String sql, Object[] params) {
		try (Connection conn = JdbcConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * # ResultSet이 있는 쿼리 : select
	 * @param sql(String) : DB에 실행 될 쿼리문
	 * @param params(Object[]) : ?에 매칭 될 데이터
	 * @return List<DataMap<String,Object>> : 쿼리 실행의 결과를 DataMap<컬럼명,데이터>의 리스트로 반환
	 */
	public List<DataMap<String, Object>> simpleQuerySelect(String sql, Object[] params) {
		List<DataMap<String, Object>> list = new ArrayList<>();
		try (Connection conn = JdbcConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			try (ResultSet rs = pstmt.executeQuery();) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				while (rs.next()) {
					DataMap<String, Object> map = new DataMap<String, Object>();
					for (int i = 1; i <= columnCount; i++) {
						map.put(rsmd.getColumnName(i), rs.getObject(i));
					}
					list.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * # ResultSet 없는 멀티쿼리 : insert, update, delete
	 * @param conn : 트랜잭션을 위한 Connection
	 * @param sql : 쿼리문
	 * @param params : PreparedStatement의 ?에 매칭될 데이터
	 * @return boolean 결과값
	 */
	public boolean multiQuery(Connection conn, String sql, Object[] params) {
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * # ResultSet 있는 멀티쿼리 : select
	 * @param conn : 트랜잭션을 위한 커넥션
	 * @param sql : 쿼리문
	 * @param params : PreparedStatement의 ?에 매칭될 데이터
	 * @return
	 */
	public List<DataMap<String, Object>> multiQuerySelect(Connection conn, String sql, Object[] params) {
		List<DataMap<String, Object>> list = new ArrayList<>();
		try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			try (ResultSet rs = pstmt.executeQuery();) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				while (rs.next()) {
					DataMap<String, Object> map = new DataMap<>();
					for (int i = 1; i <= columnCount; i++) {
						Object obj = rs.getObject(i);
						if (obj != null)
							map.put(rsmd.getColumnName(i), obj);
					}
					if (map.size() > 0)
						list.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * # 해당 쿼리가 트랜잭션으로 묶이는 작업인지 아닌지 분별해서 작업 후 결과물 돌려주는 함수 - insert, update, delete
	 * @param conn : 트랜잭션 판별을 위한 conn (simpleQuery 에는 null)
	 * @param sql(String) : DB 동작 쿼리문
	 * @param params(Object[]) : ?에 매칭될 데이터 
	 * @return boolean 결과
	 */
	public boolean queryExecute(Connection conn, String sql, Object[] params) {
		if (conn == null) {
			return simpleQuery(sql, params);
		} else {
			return multiQuery(conn, sql, params);
		}
	}
	
	/**
	 * # 해당 쿼리가 트랜잭션으로 묶이는 작업인지 아닌지 분별해서 작업 후 결과물 돌려주는 함수 - select
	 * @param conn : 트랜잭션에 따른 분별 작업 후 select 값이 있는 쿼리 처리
	 * @param sql(String) : DB 동작 쿼리문
	 * @param params(Object[]) : ?에 매칭될 데이터 
	 * @return List<DataMap<String,Object>> : 쿼리 실행의 결과를 DataMap<컬럼명,데이터>의 리스트로 반환
	 */
	public List<DataMap<String, Object>> queryExecuteSelect(Connection conn, String sql, Object[] params) {
		if (conn == null) {
			return simpleQuerySelect(sql, params);
		} else {
			return multiQuerySelect(conn, sql, params);
		}
	}
}
