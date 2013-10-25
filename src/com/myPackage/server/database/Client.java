package com.myPackage.server.database;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
 


@PersistenceCapable
public class Client {
    

	/** 
	 * Client representa al data object, de los clientes del sistema.
	 */  
 
      
	public Client(String username, Date date) {
		this.username = username;
		this.date =  new Date();

	}


	@PrimaryKey
	@Persistent
	private String username;
	
	@Persistent
	private Date date;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
}
