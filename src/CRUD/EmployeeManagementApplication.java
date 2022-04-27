package CRUD;

import java.sql.*;
import java.util.Scanner;


public class EmployeeManagementApplication {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException

	{
		try 
		{
			int choice = 0;
			employee e = new employee();

			do
			{
				System.out.println("Select an operation \n 1- Create \n 2- Update \n 3- Delete a Record \n 4- Search for an Employee \n 5- Exit");
				Scanner choicein = new Scanner(System.in);
				choice = choicein.nextInt();

				switch(choice)
				{
				case 1:
					e.getEmployeeDetails();
					e.insertEmployee();
					break;
				case 2:
					e.updateEmployeeRecord();
					break;
				case 3:
					e.deleteEmployeeRecord();
					break;
				case 4: 
					e.searchEmployee();
					break;
				case 5:
					break;
				default:
					System.out.println("Please select a correct number");
				}
			}
			while(choice!=5);
			System.out.println("Goodbye!");
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	static class employee
	{
		String username;
		String fullname;
		String email;
		String password;

		public void getEmployeeDetails()
		{
			Scanner input = new Scanner(System.in);
			System.out.println("Enter your Username");
			username = input.nextLine();
			System.out.println("Enter your Full Name");
			fullname = input.nextLine();
			System.out.println("Enter your Email Address");
			email = input.nextLine();
			System.out.println("Enter your Password");
			password = input.nextLine();
			input.close();
		}

		public void insertEmployee() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
		{
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb", "root", "root");
			String sql = "INSERT INTO users (username, fullname, email, password)" + "VALUES (?,?,?,?);";
			PreparedStatement stmt = connection.prepareStatement(sql); 
			stmt.setString(1, username);
			stmt.setString(2, fullname);
			stmt.setString(3, email);
			stmt.setString(4, password);
			int i = stmt.executeUpdate();
			System.out.println("Record  inserted successfully");
			connection.close();
		}

		public void updateEmployeeRecord() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
		{
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb", "root", "root");
			Scanner input = new Scanner(System.in);
			System.out.println("Select an identifier type i.e username");
			String fieldSearch = input.nextLine();
			System.out.println("Select a feild to update i.e email");
			String fieldUpdate = input.nextLine();
			System.out.println("input your updated info, i.e your new email");
			String newData = input.nextLine();
			System.out.println("Enter your identifier ");
			String searchData = input.nextLine();
			String sql = "UPDATE users SET " + fieldUpdate + " =?" + " WHERE " + fieldSearch + " =?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, newData);
			stmt.setString(2, searchData);
			int i = stmt.executeUpdate();
			input.close();
			if(i>0)
			{
				System.out.println("Record updated sucessfully");
			}else
			{
				System.out.println("No Such record in the Database");
			}

			connection.close();
		}
		public void deleteEmployeeRecord() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
		{
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb", "root", "root");
			System.out.println("Enter the fullname of the Employee");
			Scanner input = new Scanner(System.in);
			String inputname=input.nextLine();
			String sql = "DELETE FROM users WHERE fullname = ?;";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, inputname);
			int i = stmt.executeUpdate();
			input.close();
			if(i>0)
			{
				System.out.println("Record Deleted Successfully");
			}
			else
			{
				System.out.println("No Such Record in the Database");
			}

			connection.close();
		}

		public void searchEmployee() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
		{
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb", "root", "root");
			System.out.println("Enter Your fullname");
			Scanner input = new Scanner(System.in);
			String inputfullname=input.nextLine();
			String sql = "SELECT * FROM users WHERE fullname=?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, inputfullname);
			ResultSet rs = stmt.executeQuery();
			input.close();
			if(rs.next()==false)
			{
				System.out.println("No such record found in the database");
			}
			else
			{    
				String username = rs.getString(1);
				String fullname = rs.getString(2);
				String email = rs.getString(3);
				String password = rs.getString(4);
				System.out.println("UserName: "+ username + " Fullname: " + fullname +" Email: " + email + "Password: " + password);

			}

			connection.close();
		}
	}	 
}

