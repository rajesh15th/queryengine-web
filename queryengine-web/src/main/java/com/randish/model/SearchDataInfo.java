package com.randish.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchDataInfo {
	
	private String searchId = null;
	private String searchWord = null;
	private boolean searchRunning = false;
	private boolean searchCompleted = false;
	private boolean terminateSearch = false;
	private boolean searchFailed = false;
	private List<String> totalTables = null;
	private List<String> pendingTables = null;
	private List<String> completedTables = new ArrayList<>();
	private List<String> processingTables = new ArrayList<>();
	private List<String> keyFoundTables = new ArrayList<>();
	private List<String> failedTables = new ArrayList<>();
	private DatabaseInfo databaseInfo = null;
	private List<String> messages = new ArrayList<>();
	
	public void addCompletedTableName(String tableName) {
		synchronized (completedTables) {
			completedTables.add(tableName);
		}
	}

	public void addProcessingTableName(String tableName) {
		synchronized (processingTables) {
			processingTables.add(tableName);
		}
	}

	public void removeProcessingTableName(String tableName) {
		synchronized (processingTables) {
			processingTables.remove(tableName);
		}
	}

	public void addKeyFoundTableName(String tableName) {
		synchronized (keyFoundTables) {
			keyFoundTables.add(tableName);
		}
	}

	public void removeFromPendingList(String tableName) {
		synchronized (pendingTables) {
			pendingTables.remove(tableName);
		}
	}
	
	public void addFailedTableName(String tableName) {
		synchronized (failedTables) {
			failedTables.add(tableName);
		}
	}
	
	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public boolean isSearchRunning() {
		return searchRunning;
	}

	public void setSearchRunning(boolean searchRunning) {
		this.searchRunning = searchRunning;
	}

	public boolean isSearchCompleted() {
		return searchCompleted;
	}

	public void setSearchCompleted(boolean searchCompleted) {
		this.searchCompleted = searchCompleted;
	}

	public boolean isSearchFailed() {
		return searchFailed;
	}

	public void setSearchFailed(boolean searchFailed) {
		this.searchFailed = searchFailed;
	}

	public boolean isTerminateSearch() {
		return terminateSearch;
	}

	public void setTerminateSearch(boolean terminateSearch) {
		this.terminateSearch = terminateSearch;
	}

	public List<String> getTotalTables() {
		return totalTables;
	}

	public void setTotalTables(List<String> totalTables) {
		this.totalTables = new ArrayList<String>(totalTables);
	}

	public List<String> getCompletedTables() {
		return completedTables;
	}

	public void setCompletedTables(List<String> completedTables) {
		this.completedTables = completedTables;
	}

	public List<String> getProcessingTables() {
		return processingTables;
	}

	public void setProcessingTables(List<String> processingTables) {
		this.processingTables = processingTables;
	}

	public List<String> getKeyFoundTables() {
		return keyFoundTables;
	}

	public void setKeyFoundTables(List<String> keyFoundTables) {
		this.keyFoundTables = keyFoundTables;
	}

	public List<String> getPendingTables() {
		return pendingTables;
	}

	public void setPendingTables(List<String> pendingTables) {
		this.pendingTables = new ArrayList<String>(totalTables);
	}

	public List<String> getFailedTables() {
		return failedTables;
	}

	public void setFailedTables(List<String> failedTables) {
		this.failedTables = failedTables;
	}

	public DatabaseInfo getDatabaseInfo() {
		return databaseInfo;
	}

	public void setDatabaseInfo(DatabaseInfo databaseInfo) {
		this.databaseInfo = databaseInfo;
	}

	public List<String> getMessages() {
		return messages;
	}
	public void addMessage(String message) {
		getMessages().add(new Date() + "\t" + message);
	}

	
	
	
}