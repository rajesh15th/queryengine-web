package com.randish.search;

import java.sql.Connection;
import java.util.List;

import org.springframework.jdbc.support.JdbcUtils;

import com.randish.exceptions.QueryEngineException;
import com.randish.model.SearchDataInfo;
import com.randish.util.DatabaseUtil;

public class InitiateSearchProcess implements Runnable {

	SearchDataInfo searchDataInfo;
	String searchKey;
	int index = 0;
	Connection con;

	public InitiateSearchProcess(SearchDataInfo searchDataInfo) throws Exception {
		super();
		this.searchDataInfo = searchDataInfo;
		this.searchKey = searchDataInfo.getSearchWord();
		con = DatabaseUtil.getConnection(searchDataInfo.getDatabaseInfo());
	}

	@Override
	public void run() {
		try {
			startExecution();
		} catch (Exception e) {
			e.printStackTrace();
			searchDataInfo.addMessage(e.getMessage());
		} finally {
			JdbcUtils.closeConnection(con);
		}
	}

	private void startExecution() throws Exception {

		List<String> tableNames = DatabaseUtil.getTableNames(searchDataInfo.getDatabaseInfo());
		searchDataInfo.setTotalTables(tableNames);
		searchDataInfo.setPendingTables(tableNames);
		tableNames = searchDataInfo.getTotalTables();
		int tablesCount = tableNames.size();
		while (index < tablesCount) {
			if (searchDataInfo.isTerminateSearch()) {
				String msg = "Process terminated with id " + searchDataInfo.getSearchId();
				System.out.println(msg);
				searchDataInfo.addMessage(msg);
				break;
			}
			try {
				new Thread(new QueryEngineSearch(searchDataInfo, con, tableNames.get(index), searchKey)).run();
				index++;
			} catch (QueryEngineException e) {
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

}
