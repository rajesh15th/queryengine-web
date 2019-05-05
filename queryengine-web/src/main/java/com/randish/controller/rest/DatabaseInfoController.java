package com.randish.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.randish.model.DataResponse;
import com.randish.model.DatabaseInfo;
import com.randish.service.DatabaseInfoService;
import com.randish.util.DatabaseUtil;

@RestController
@RequestMapping("/database-info")
public class DatabaseInfoController {
	List<DatabaseInfo> data = null;

	@Autowired
	DatabaseInfoService databaseInfoService;
	
	@GetMapping
	public DataResponse getAllInfo() {
		return new DataResponse(databaseInfoService.getAllDatabaInfoList());
	}

	@GetMapping("/{id}")
	public DataResponse getInfoById(@PathVariable int id) {
		return new DataResponse(databaseInfoService.findById(id));
	}

	@PostMapping
	public DataResponse addNewInfo(@RequestBody DatabaseInfo newInfo) {
		try {
			DatabaseUtil.validateConnection(newInfo);
		} catch (Exception e) {
			return new DataResponse("ERROR", e.getMessage());
		}
		return new DataResponse(databaseInfoService.insert(newInfo));
	}

	@PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
	public DataResponse updaByIdteInfo(@PathVariable int id, @RequestBody DatabaseInfo updateInfo) {
		updateInfo.setId(id);
		try {
			DatabaseUtil.validateConnection(updateInfo);
		} catch (Exception e) {
			return new DataResponse("ERROR", e.getMessage());
		}
		return new DataResponse(databaseInfoService.update(updateInfo));
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		databaseInfoService.deleteById(id);
	}
	
	

}
