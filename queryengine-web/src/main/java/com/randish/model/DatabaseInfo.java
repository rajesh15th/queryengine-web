package com.randish.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQuery(name="find_all_Databases",query="select p from DatabaseInfo p order by p.id")
public class DatabaseInfo {
	@Id
	@SequenceGenerator(name="databseinfoidseq", sequenceName="database_info_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="databseinfoidseq")
	int id;
	String name;
	int type;
	String driverName;
	String url;
	String userName;
	String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DatabaseInfo(int id, String name, int type, String driverName, String url, String userName,
			String password) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.driverName = driverName;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	public DatabaseInfo(String name, int type, String driverName, String url, String userName, String password) {
		super();
		this.name = name;
		this.type = type;
		this.driverName = driverName;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	@Override
	public String toString() {
		return "DatabaseInfo [id=" + id + ", name=" + name + ", type=" + type + ", driverName=" + driverName
				+ ", url=" + url + ", userName=" + userName + ", password=" + password + "]";
	}

	public DatabaseInfo() {
	}

	public void copy(DatabaseInfo updateInfo) {
		this.name = updateInfo.getName();
		this.type = updateInfo.getType();
		this.driverName = updateInfo.getDriverName();
		this.url = updateInfo.getUrl();
		this.userName = updateInfo.getUserName();
		this.password = updateInfo.getPassword();
	}

}
