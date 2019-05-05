package com.randish.search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.support.JdbcUtils;

import com.randish.exceptions.QueryEngineException;
import com.randish.model.SearchDataInfo;
import com.randish.util.DatabaseUtil;
import com.randish.util.StringUtil;

public class QueryEngineSearch implements Runnable {

	private SearchDataInfo searchDataInfo;
	private String tableName, searchWord;
	private Connection con;
	final static int MAX_NO_OF_SEARCH_PROCESSES = 8;
	static Integer currentRunningProcesses = 0;
	
	public QueryEngineSearch(SearchDataInfo searchDataInfo, Connection con, String tableName, String searchWord) throws Exception {
		
		if ( MAX_NO_OF_SEARCH_PROCESSES == currentRunningProcesses ) {
			throw new QueryEngineException("Max no of process limit reached (" + MAX_NO_OF_SEARCH_PROCESSES + ")");
		}
		synchronized (currentRunningProcesses) {
			currentRunningProcesses++;
		}
		
		this.searchDataInfo = searchDataInfo;
		this.con = con;
		this.tableName = tableName;
		this.searchWord = searchWord;
		searchDataInfo.addProcessingTableName(tableName);
		
	}

	@Override
	public void run() {
		try {
			boolean searchWordFound = startExtraction();
			if ( searchWordFound ) {
				searchDataInfo.addKeyFoundTableName(tableName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			searchDataInfo.addFailedTableName(tableName);
		} finally {
			synchronized (currentRunningProcesses) {
				currentRunningProcesses--;
			}
			searchDataInfo.addCompletedTableName(tableName);
			searchDataInfo.removeProcessingTableName(tableName);
			searchDataInfo.removeFromPendingList(tableName);
		}
	}

	private boolean startExtraction() throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(DatabaseUtil.getSqlStatement(tableName));
			rs = stmt.executeQuery();
			return searchData(searchWord, rs);
		} catch (Exception e) {
			throw e;
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
		}
	}

	private boolean searchData(String searchWord2, ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		if (rs.next()) {
			do {
				for (int i = 1; i <= columnCount; i++) {
					String colData = rs.getString(i);
					if (StringUtil.isNotBlank(colData) && colData.equalsIgnoreCase(searchWord2)) {
						return true;
					}
				}
			} while (rs.next());
		}
		return false;
	}
}