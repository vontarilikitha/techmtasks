package Files;
import java.io.File;
public class Directoryornot {
  public static void main(String[] args) {
	  String pathname="C:\\Users\\keshi\\Downloads";
	  File file=new File(pathname);
	  if(!file.exists()) {
		  System.out.println("Pathname does not exist: " + pathname);
	  }
	  if (file.isDirectory()) {
          System.out.println(pathname + " is a directory.");
      }
	  else if (file.isFile()) {
          System.out.println(pathname + " is a file.");
      } 
	  else {
		  System.out.println(pathname + " is neither a file nor a directory (special file?).");
	  }
  }
}
