package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ParkSysDataSource {
	private static final String URL_TEMPLATE = "jdbc:mysql://localhost/%s?user=%s&password=%s";
	
	private Connection getConnection(String dbName, String username, String password) throws SQLException {
		String url = String.format(URL_TEMPLATE, dbName, username, password);
		return DriverManager.getConnection(url);
	}

	public Connection getConnection() throws SQLException {
		return getConnection("projetodsi", "admin_projetodsi", "projetodsi123");
	}
}
