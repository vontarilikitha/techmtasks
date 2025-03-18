import java.sql.*;
import java.util.Scanner;
public class Updaterow {
    public static void main(String [] args) throws SQLException{
    	try {
    	String url="jdbc:mysql://localhost:3306/sushanth";
  	  String user="root";
  	  String pass="root";
  	 Scanner sc=new Scanner(System.in); 
  	int roll=sc.nextInt();
	 sc.nextLine();
	 Connection conn=DriverManager.getConnection(url,user,pass);
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
}
