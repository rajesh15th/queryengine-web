package com.randish.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randish.controller.rest.DatabaseInfoRepository;
import com.randish.model.DatabaseInfo;
@Service
public class DatabaseInfoService {
	
	@Autowired
	DatabaseInfoRepository databaseInfoRepository;
	
	public List<DatabaseInfo> getAllDatabaInfoList() {
		return databaseInfoRepository.findAll();
	}
	
	public DatabaseInfo findById(int id) {
		return databaseInfoRepository.findById(id);
	}
	

	public DatabaseInfo update(DatabaseInfo databaseInfo) {
		return databaseInfoRepository.update(databaseInfo);
	}

	public DatabaseInfo insert(DatabaseInfo databaseInfo) {
		return databaseInfoRepository.insert(databaseInfo);
	}

	public void deleteById(int id) {
		databaseInfoRepository.deleteById(id);
	}
	
}
