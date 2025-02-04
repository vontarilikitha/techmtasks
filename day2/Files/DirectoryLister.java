package Files;
import java.io.File;
import java.io.IOException;
public class DirectoryLister {
	 public static void main(String[] args) {
	 String directoryPath = "C:\\Users\\keshi\\OneDrive\\Desktop\\scholarship";
	  try {
          listFilesInDirectory(directoryPath);
      } catch (IOException e) {
          System.err.println("Error: " + e.getMessage());
          e.printStackTrace(); // For debugging
      }
}
	 public static void listFilesInDirectory(String directoryPath) throws IOException {

	  File directory = new File(directoryPath);

      if (!directory.exists() || !directory.isDirectory()) {
          throw new IOException("Invalid directory path: " + directoryPath);
      }

      String[] fileNames = directory.list();
      if (fileNames != null) { 
          System.out.println("Files/Directories in " + directoryPath );
          for (String fileName : fileNames) {
              System.out.println(fileName);
          } 
}
	 }
}