import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.*;

public class deleterow {
  public static void main(String [] args) {
	  String url="jdbc:mysql://localhost:3306/sushanth";
  	  String user="root";
  	  String pass="root";
  	  try {
  		  
  	  
  	  Scanner sc=new Scanner(System.in);
  	  int idtodelete=sc.nextInt();
  	  Connection conn=DriverManager.getConnection(url,user,pass);
      PreparedStatement stmt=conn.prepareStatement("DELETE FROM ece WHERE roll=?");
      stmt.setInt(1, idtodelete);
      stmt.executeUpdate();
  	  }
  catch(Exception e) {
	  System.out.println(e);
  }
  }
}
