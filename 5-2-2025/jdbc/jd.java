import java.sql.*;
public class jd {
  public static void main(String [] args) throws SQLException, ClassNotFoundException{
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/sushanth","root","root");
    Statement stmt=conn.createStatement();
    ResultSet rs=stmt.executeQuery("Select * from ece");
    while(rs.next()) {
		System.out.println(rs.getString(1));
		System.out.println(rs.getInt(2));
		System.out.println(rs.getInt(3));
	}
    rs.close();
	stmt.close();
	conn.close();
  }
  
}
