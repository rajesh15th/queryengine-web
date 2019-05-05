package com.randish.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.randish.model.DataResponse;
import com.randish.model.DatabaseInfo;
import com.randish.model.SearchDataInfo;
import com.randish.search.HandleSearchExecution;
import com.randish.service.DatabaseInfoService;

@RestController
public class QueryEngineRestController {
	@Autowired 
	HandleSearchExecution handleSearchExecution;
	
	@Autowired
	DatabaseInfoService databaseInfoService;

	@GetMapping("/processdata")
	public DataResponse processdata(@RequestParam(value = "searchWord", required = false) String searchKey
			,@RequestParam(value = "dbInfoId", required = false) int dbInfoId) {
		System.out.println("critaria " + searchKey);
		DatabaseInfo databaseInfo = databaseInfoService.findById(dbInfoId);
		try {
			return new DataResponse(handleSearchExecution.searchData(databaseInfo, searchKey));
		} catch (Exception e) {
			return new DataResponse("ERROR", e.getMessage());
		}
	}

	@GetMapping("/tableData")
	public DataResponse tableData(@RequestParam(value = "searchId", required = false) String searchId,
			@RequestParam(value = "tName", required = false) String tableName) {
		try {
			return new DataResponse(handleSearchExecution.getSearchDataOfTable(searchId,tableName));
		} catch (Exception e) {
			return new DataResponse("ERROR", e.getMessage());
		}
	}
	@GetMapping("/terminateSearchProcess")
	public DataResponse terminateSearchProcess(@RequestParam(value = "searchId", required = false) String searchId,
			@RequestParam(value = "tName", required = false) String tableName) {
		try {
			SearchDataInfo searchDataInfo = handleSearchExecution.getSearchDataInfo(searchId);
			if (searchDataInfo != null) {
				searchDataInfo.setTerminateSearch(true);
				return new DataResponse("SUCCESS","Terminated");
			} else {
				return new DataResponse("NOTFOUND","Execution details not found");
			}
		} catch (Exception e) {
			return new DataResponse("ERROR", e.getMessage());
		}
	}
	
	@GetMapping("/processedData")
	public DataResponse processedData(@RequestParam(value = "searchId", required = false) String searchId) {
		try {
			SearchDataInfo searchDataInfo = handleSearchExecution.getSearchDataInfo(searchId);
			if (searchDataInfo == null) {
				return new DataResponse("NOTFOUND","Execution details not found");
			}
			return new DataResponse(searchDataInfo);
		} catch (Exception e) {
			return new DataResponse("ERROR", e.getMessage());
		}
	}
	
	
}
