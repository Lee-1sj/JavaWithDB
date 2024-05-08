package kh.student;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLStudentConnect {
	public static Connection makeConnection() throws FileNotFoundException, IOException {
		String filePath = "D:/doitjava/doitjava/studentDB/src/kh/student/db.properties";
		Properties properties = new Properties();
		properties.load(new FileReader(filePath));
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");

		Connection con = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//System.out.println("jdbc driver load success");
			con = DriverManager.getConnection(url, user, password);
			//System.out.println("db connect success");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("jdbc driver load fail");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("db connection fail");
		}
		return con;
		
	}
}
