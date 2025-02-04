package Files;
import java.io.File;
import java.io.IOException;
public class Extensions {
	  public static void main(String[] args) {
	        String directoryPath = "."; // Current directory, or replace with your path
	        String[] extensions = {"txt", "pdf", "java"}; // Specify the extensions you want

	        try {
	            listSpecificFiles(directoryPath, extensions);
	        } catch (IOException e) {
	            System.err.println("Error: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	  public static void listSpecificFiles(String directoryPath, String[] extensions) throws IOException {
		  File directory=new File(directoryPath);
		  if (!directory.exists() || !directory.isDirectory()) {
	            throw new IOException("Invalid directory path: " + directoryPath);
	        }
		  
	  }
}
