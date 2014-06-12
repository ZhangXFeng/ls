package com.locationService.dba;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;





import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.dbcp.BasicDataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class DBClient {
	private static final Log LOG = LogFactory.getLog(DBClient.class);

	private static final String url="jdbc:mysql://localhost:3306/locationservice?user=root&password=zxf1105";
//	 private static final ComboPooledDataSource dataSource = new
//	 ComboPooledDataSource();

//	private static BasicDataSource dataSource;
	static {
		Properties properties = new Properties();
		try {
			properties.load(DBClient.class.getClassLoader()
					.getResourceAsStream("dbcp.properties"));
		} catch (IOException e) {
			LOG.error(e);
			System.exit(-1);
		}
		

//		dataSource =new BasicDataSource();
//		dataSource.setDriverClassName(properties.getProperty("com.mysql.jdbc.Driver"));
//		dataSource.setUsername(properties.getProperty("user"));
//		dataSource.setUrl(properties.getProperty("url"));
//		dataSource.setPassword(properties.getProperty("password"));
		
		 try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private DBClient() {

	}

	public static void executeSql(String sql) throws SQLException {
		LOG.info(sql);
		Connection connection = null;
		Statement statement = null;
		try {
//			connection = dataSource.getConnection();
			connection=DriverManager.getConnection(url);
			statement = connection.prepareStatement(sql);
			statement.execute(sql);
		} catch (SQLException e) {
			if (connection != null) {
				connection.close();
				if (statement != null) {
					statement.close();
				}
			}
			throw e;
		}

	}

	public static ResultSet executeSqlForResult(String sql) throws SQLException {
		LOG.info(sql);
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
//			connection = dataSource.getConnection();
			connection=DriverManager.getConnection(url);
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			if (connection != null) {
				connection.close();
				if (statement != null) {
					statement.close();
				}
			}

			throw e;
		}

		return resultSet;
	}

	public static void main(String[] args) throws SQLException {

		String sql = "select username from userinfo;";

		ResultSet rs = DBClient.executeSqlForResult(sql);
		while (rs.next()) {
			System.out.println(rs.getString("username"));
		}
	}
}
