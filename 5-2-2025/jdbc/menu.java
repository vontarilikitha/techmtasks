import java.sql.*;
import java.util.Scanner;
public class menu {
	  public static String url="jdbc:mysql://localhost:3306/sushanth";
  	  public static String user="root";
  	  public static String pass="root";
  	  
  		
  	  
	 public static void main(String [] args) throws SQLException{
		 Scanner sc=new Scanner(System.in);
	  	  int n=sc.nextInt();
	  	  do {
	  	  
	  		  switch(n) {
	  		  case 1:
	  			  insert();
	  			  break;
	  		  case 2:
	  			  update();
	  		  case 3:
	  			  delete();
	  		  case 4:
	  			  select();
	  		  case 5:
	  			  System.out.println("Exiting Program");
	  		  default:
	  			System.out.println("Invalid choice. Please try again.");
	  		  }}while(n!=5) ;
	  	  
}
	 public static void insert() throws SQLException {
		 
		 try {
		 Connection conn=DriverManager.getConnection(url,user,pass);
		 Scanner sc=new Scanner(System.in);
		 System.out.print("Enter name: ");
	        String Name = sc.nextLine();
	        System.out.print("Enter roll: ");
	        int  roll = sc.nextInt();
	        System.out.print("Enter age: ");
	        int age = sc.nextInt();
	     PreparedStatement stmt=conn.prepareStatement("INSERT INTO ece VALUES(?,?,?)");
	     stmt.setString(1, Name);
	     stmt.setInt(2, roll);
	     stmt.setInt(3, age);
	     int rowsaffected=stmt.executeUpdate();
	     if(rowsaffected>0) {
	    	 System.out.println("inserted successfully");
	     }
	 }
		 catch(Exception e) {
			 System.out.println(e);
		 }
	     
	 }
	 public static void update() throws SQLException{
		 try {
			 Connection conn=DriverManager.getConnection(url,user,pass);
			 Scanner sc=new Scanner(System.in); 
			 int roll=sc.nextInt();
			 sc.nextLine();
			 System.out.print("Enter the column to update (name, age): ");
			 String columnToUpdate = sc.nextLine();
		      System.out.print("Enter the new value: ");
		        String newValueString = sc.nextLine();
			 PreparedStatement stmt=conn.prepareStatement("UPDATE ece SET "+ columnToUpdate + " = ? WHERE roll=?");
			 stmt.setString(1, newValueString);
			 stmt.setInt(2, roll);
			 int ra=stmt.executeUpdate();
			 if (ra > 0) {
	                System.out.println("Row updated successfully.");
	            } else {
	                System.out.println("No row found with that ID.");
	            }
		 }
		 catch(Exception e) {
			 System.out.println(e);
		 }
	 }
	 public static void delete() throws SQLException{
		 try {
			 Connection conn=DriverManager.getConnection(url,user,pass);
			 Scanner sc=new Scanner(System.in); 
			 int roll=sc.nextInt();
			 PreparedStatement stmt=conn.prepareStatement("DELETE FROM ece WHERE roll=?");
			 stmt.setInt(1, roll);
			 int rowsAffected = stmt.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Row deleted successfully.");
	            } else {
	                System.out.println("No row found with that ID.");
	            }
		 }
		 catch(Exception e) {
			 System.out.println(e);
		 }
	 }
	 public static void select () throws SQLException{
		 try {
			 Connection conn=DriverManager.getConnection(url,user,pass);
			 PreparedStatement stmt=conn.prepareStatement("SELECT * FROM ece");
			 ResultSet rs=stmt.executeQuery();
			 while(rs.next()) {
				 System.out.println(rs.getString(1));
					System.out.println(rs.getInt(2));
					System.out.println(rs.getInt(3));
			 }
			 
		 }
		 catch(Exception e) {
			 System.out.println(e);
		 }
	 }
}
