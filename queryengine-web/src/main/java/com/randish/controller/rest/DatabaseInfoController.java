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

import com.randish.model.DatabaseInfo;
import com.randish.service.DatabaseInfoService;

@RestController
@RequestMapping("/database-info")
public class DatabaseInfoController {
	List<DatabaseInfo> data = null;

	@Autowired
	DatabaseInfoService databaseInfoService;
	
	@GetMapping
	public List<DatabaseInfo> getAllInfo() {
		return databaseInfoService.getAllDatabaInfoList();
	}

	@GetMapping("/{id}")
	public DatabaseInfo getInfoById(@PathVariable int id) {
		return databaseInfoService.findById(id);
	}

	@PostMapping
	public DatabaseInfo addNewInfo(@RequestBody DatabaseInfo newInfo) {
		return databaseInfoService.insert(newInfo);
	}

	@PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
	public DatabaseInfo updaByIdteInfo(@PathVariable int id, @RequestBody DatabaseInfo updateInfo) {
		updateInfo.setId(id);
		return databaseInfoService.update(updateInfo);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		databaseInfoService.deleteById(id);
	}
	
	

}
