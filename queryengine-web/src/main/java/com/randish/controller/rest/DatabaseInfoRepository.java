package com.randish.controller.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.randish.model.DatabaseInfo;

@Repository
@Transactional
public class DatabaseInfoRepository {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<DatabaseInfo> findAll() {
		TypedQuery<DatabaseInfo> namedQuery = entityManager.createNamedQuery("find_all_Databases", DatabaseInfo.class);
		return namedQuery.getResultList();
	}
	
	public DatabaseInfo findById(int id) {
		return entityManager.find(DatabaseInfo.class, id);// JPA
	}

	public DatabaseInfo update(DatabaseInfo databaseInfo) {
		return entityManager.merge(databaseInfo);
	}

	public DatabaseInfo insert(DatabaseInfo databaseInfo) {
		return entityManager.merge(databaseInfo);
	}

	public void deleteById(int id) {
		DatabaseInfo DatabaseInfo = findById(id);
		entityManager.remove(DatabaseInfo);
	}
	
}
