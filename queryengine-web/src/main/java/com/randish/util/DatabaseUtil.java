package com.randish.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.JdbcUtils;

import com.randish.constants.DatabaseTypes;
import com.randish.model.DatabaseInfo;

public class DatabaseUtil {

	public static void validateConnection(DatabaseInfo databaseInfo) throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		String dummySelectQuery = null;
		try {
			con = getConnection(databaseInfo);
			dummySelectQuery = getDummySelectQuery(databaseInfo.getType());
			pst = con.prepareStatement(dummySelectQuery);
			pst.executeQuery();
		} catch (Exception e) {
			throw e;
		} finally {
			JdbcUtils.closeStatement(pst);
			JdbcUtils.closeConnection(con);
		}
	}

	public static Connection getConnection(DatabaseInfo databaseInfo) throws Exception {
		Connection con = null;
		try {
			String driverName = databaseInfo.getDriverName();
			String jdbcUrl = databaseInfo.getUrl();
			String userName = databaseInfo.getUserName();
			String password = databaseInfo.getPassword();

			Class.forName(driverName);
			con = DriverManager.getConnection(jdbcUrl, userName, password);

		} catch (Exception e) {
			throw e;
		}
		return con;
	}

	public static String getSqlStatement(String tableName) {
		return "select * from " + tableName;
	}
	

	public static List<String> getTableNames(DatabaseInfo databaseInfo) throws Exception {
		List<String> tableNames = new ArrayList<>();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String tableNamesQuery = null;
		try {
			con = getConnection(databaseInfo);
			tableNamesQuery = getTablesQuery(databaseInfo.getType());
			pst = con.prepareStatement(tableNamesQuery);
			rs = pst.executeQuery();
			while (rs.next()) {
				tableNames.add(rs.getString(1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(pst);
			JdbcUtils.closeConnection(con);
		}
		return tableNames;
	}

	public static List<List<String>> parseResultsToList(String searchWord, String tableName, DatabaseInfo databaseInfo) throws Exception {
		Connection connection = null;
		try {
			connection = getConnection(databaseInfo);
			return parseResultsToList(searchWord, tableName, connection);
		} finally {
			JdbcUtils.closeConnection(connection);
		}

	}


	public static List<List<String>> parseResultsToList(String searchWord, String tableName, Connection con) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(getSqlStatement(tableName));
			rs = stmt.executeQuery();
			return parseResultsToList(searchWord, rs);
		} catch (Exception e) {
			throw e;
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
		}
	}

	public static List<List<String>> parseResultsToList(String searchWord, ResultSet rs) throws SQLException {
		List<List<String>> data = new ArrayList<>();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		List<String> columnInfo = new ArrayList<>();
		columnInfo.add("Row Number");
		for (int i = 1; i <= columnCount; i++) {
			columnInfo.add(metaData.getColumnLabel(i));
		}
		data.add(columnInfo);
		
		if (rs.next()) {
			int rowCount = 1;
			do {
				boolean isWordFound = false;
				for (int i = 1; i <= columnCount; i++) {
					String colData = rs.getString(i);
					if (StringUtil.isNotBlank(colData) && colData.equalsIgnoreCase(searchWord)) {
						isWordFound = true;
						break;
					}
				}
				if (isWordFound) {
					List<String> rowData = new ArrayList<>();
					rowData.add(rowCount+"");
					for (int i = 1; i <= columnCount; i++) {
						rowData.add(rs.getString(i));
					}
					data.add(rowData);
				}
				rowCount++;
			} while (rs.next());
		}
		return data;
	}
	
	
	public static String getDummySelectQuery(int type) {
		String sql = "";
		switch (type) {
		case DatabaseTypes.MSSQL:
		case DatabaseTypes.POSTGRESQL:
			sql = "SELECT 'Hello world' as name";
			break;
		case DatabaseTypes.ORACLE:
		case DatabaseTypes.HANA:
			sql = "SELECT 'Hello world' as name from dual";
			break;
		}
		return sql;
	}
	
	public static String getTablesQuery(int type) {
		String sql = "";
		switch (type) {
		case DatabaseTypes.MSSQL:
			sql = "SELECT TABLE_SCHEMA+'.'+TABLE_NAME as TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE  TABLE_TYPE='BASE TABLE' order by table_name;";
			break;
		case DatabaseTypes.POSTGRESQL:
			sql = "SELECT concat(table_schema,'.',table_name) as table_name FROM INFORMATION_SCHEMA.TABLES WHERE  table_schema='public' and TABLE_TYPE='BASE TABLE' order by table_name;";
			break;
		case DatabaseTypes.ORACLE:
		case DatabaseTypes.HANA:
			sql = "SELECT 'Hello world' as name from dual";
			break;
		}
		return sql;
	}
	

}
