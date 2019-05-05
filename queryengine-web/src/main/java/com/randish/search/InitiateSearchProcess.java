package com.randish.search;

import java.util.Date;
import java.util.List;

import com.randish.exceptions.QueryEngineException;
import com.randish.model.SearchDataInfo;
import com.randish.util.DatabaseUtil;
import com.randish.util.DateUtil;

public class InitiateSearchProcess implements Runnable {

	SearchDataInfo searchDataInfo;
	String searchKey;
	int index = 0;

	public InitiateSearchProcess(SearchDataInfo searchDataInfo) throws Exception {
		super();
		this.searchDataInfo = searchDataInfo;
		this.searchKey = searchDataInfo.getSearchWord();
	}

	@Override
	public void run() {
		try {
			searchDataInfo.setStartDate(new Date());
			startExecution();
		} catch (Exception e) {
			e.printStackTrace();
			searchDataInfo.addMessage(e.getMessage());
		} finally {
			searchDataInfo.setSearchCompleted(true);
			searchDataInfo.setEndDate(new Date());
			searchDataInfo.setDateDiff(DateUtil.getDifference(searchDataInfo.getStartDate(), searchDataInfo.getEndDate()));
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
				new Thread(new QueryEngineSearch(searchDataInfo, tableNames.get(index), searchKey)).start();
				index++;
			} catch (QueryEngineException e) {
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

}
