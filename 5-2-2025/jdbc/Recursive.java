import java.sql.*;
import java.util.List;
import java.util.ArrayList;
public class Recursive {
      public static void main (String [] args) throws  SQLException{
    	  String url="jdbc:mysql://localhost:3306/sushanth";
    	  String user="root";
    	  String pass="root";
    	  try {
    		  Connection conn=DriverManager.getConnection(url,user,pass);
    		  PreparedStatement stmt=conn.prepareStatement("INSERT INTO ece(Name,roll,age) VALUES(?,?,?)");
    		  List<Object[]> list=new ArrayList<>();
    		  list.add(new Object[] {"a",27,18});
    		  list.add(new Object[] {"b",32,16});
    		  list.add(new Object[] {"c",31,19});
    		  insert(stmt,list,0);
    		  System.out.println("Data inserted successfully.");
    	  }
    	  catch(Exception e) {
    		  System.out.println(e);
    	  }
      }
      public static void insert(PreparedStatement stmt,List<Object[]> list,int index) throws SQLException {
    	  if(index>=list.size()) {
    		  return;
    	  }
    	  Object [] row=(Object[]) list.get(index);
    	  for(int i=0;i<row.length;i++) {
    		  stmt.setObject(i+1, row[i]);
    	  }
    	  stmt.executeUpdate();
    	  insert(stmt, list, index + 1);
      }
}
