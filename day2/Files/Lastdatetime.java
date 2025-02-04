package Files;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
public class Lastdatetime {
    public static void main(String[] args) {
    	 String pathname="C:\\Users\\keshi\\Downloads";
    	 try {
    		 File file=new File(pathname);
    		 
    		 if (file.exists()) {
                 long lastModifiedMillis = file.lastModified();
                 Date lastModifiedDate = new Date(lastModifiedMillis);
                 
                 System.out.println("Last modified date (File): " + formatDate(lastModifiedDate));
             } else {
                 System.out.println("File not found: " + pathname);
             }
    	 }
    	 catch(Exception e) {
 
    		 System.out.println("Error getting last modified date"+e.getMessage());
    	 }
    }

private static String formatDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(date);
}
}