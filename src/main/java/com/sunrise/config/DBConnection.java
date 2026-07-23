package com.sunrise.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static Connection getConnection() throws SQLException{
		try {
			
			Class.forName(
				DBConfig.get("db.driver")
			);
			
			return DriverManager.getConnection(
				DBConfig.get("db.url"),
				DBConfig.get("db.username"),
				DBConfig.get("db.password")
			);
		
		} catch (Exception e) {
			throw new SQLException(
			"Database connection failed"
			);
		}
		
	}

}