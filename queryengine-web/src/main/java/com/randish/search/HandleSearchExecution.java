package com.randish.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.randish.model.DatabaseInfo;
import com.randish.model.SearchDataInfo;
import com.randish.util.DatabaseUtil;

@Component
public class HandleSearchExecution {
	Map<String, SearchDataInfo> searchInformationMap = new HashMap<>();

	public String searchData(DatabaseInfo databaseInfo, String searchKey) throws Exception {
		String searchId = addnewSearchEntry(searchKey, databaseInfo);
		initiateSearch(searchInformationMap.get(searchId));
		return searchId;
	}

	private void initiateSearch(SearchDataInfo searchDataInfo) throws Exception {
			new Thread(new InitiateSearchProcess(searchDataInfo)).start(); 
	}

	private String addnewSearchEntry(String searchKey, DatabaseInfo databaseInfo) {
		String searchId = UUID.randomUUID().toString();
		SearchDataInfo searchDataInfo = new SearchDataInfo();
		searchDataInfo.setSearchId(searchId);
		searchDataInfo.setSearchWord(searchKey);
		searchDataInfo.setDatabaseInfo(databaseInfo);
		searchInformationMap.put(searchId, searchDataInfo);
		return searchId;
	}

	public List<List<String>> getSearchDataOfTable(String searchId, String tableName) throws Exception {
		SearchDataInfo searchDataInfo = getSearchDataInfo(searchId);
		return DatabaseUtil.parseResultsToList(searchDataInfo.getSearchWord(), tableName, searchDataInfo.getDatabaseInfo());
	}

	public SearchDataInfo getSearchDataInfo(String searchId) {
		return searchInformationMap.get(searchId);
	}
	
	

}
