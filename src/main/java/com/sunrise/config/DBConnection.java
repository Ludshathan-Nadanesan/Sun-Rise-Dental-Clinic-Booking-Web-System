package com.sunrise.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	private static Connection connection;
	
	public static Connection getConnection() throws Exception{
		Class.forName(
				DBConfig.get("db.driver")
				);
		
		connection = DriverManager.getConnection(
				DBConfig.get("db.url"),
				DBConfig.get("db.username"),
				DBConfig.get("db.password")
				);
		
		return connection;
	}

}