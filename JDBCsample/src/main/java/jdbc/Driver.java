package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Driver {
	
	//First we have to specify the database's url (with the spesific database name we want to use, for example I am using "company" in this example), username and the password.
	private static final String DB_URL = "url";
	private static final String DB_USERNAME = "username";
	private static final String DB_PASSWORD = "password";
	
	public static void main(String[] args) {
		
		//I used scanner to get the employee id selection from the user.
		Scanner scanner = new Scanner(System.in);
		System.out.print("Employee ID: ");
		int employeeId = scanner.nextInt();
		scanner.close();
		
		//I gave the url, username and the password to open the connection.
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			
			//"Prepared statement is used to execute parameterized queries."
			//It is benefical to use prepared statements for querying. 
			//First of all the overall performance of the application is faster with the prepared statement because the query is compiled only once.
			try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE id = ?")) {
				
				//In the query I've placed a question mark. It is the first and the only question mark, so I am specifying it by "1", and giving it the value of "employeeId".
				//If there would be another question mark after this one, it would be (2, ...). 
				statement.setInt(1, employeeId);
				
				//Executing the query in here and returning a resultSet.
				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						System.out.println(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
		

}
