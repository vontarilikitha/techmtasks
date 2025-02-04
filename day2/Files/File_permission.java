package Files;
import java.io.File;
import java.io.IOException;
public class File_permission {
	 public static void main(String[] args) {

	        String filePath = "C:\\Users\\keshi\\Downloads\\prac.txt"; // Replace with the actual path

	        File file = new File(filePath);

	        if (!file.exists()) {
	            System.out.println("File or directory does not exist: " + filePath);
	            return;
	        }

	        System.out.println("File/Directory: " + filePath);

	        if (file.canRead()) {
	            System.out.println("Read permission: Granted");
	        } else {
	            System.out.println("Read permission: Denied");
	        }

	        if (file.canWrite()) {
	            System.out.println("Write permission: Granted");
	        } else {
	            System.out.println("Write permission: Denied");
	        }

	        if (file.isDirectory()) {
	            System.out.println("It's a directory.");
	        } else if (file.isFile()) {
	            System.out.println("It's a file.");
	        }
	 }


}
