package Files;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
public class ReadPlain {
  public static void  main(String [] args) {
	  String filename="C:\\Users\\keshi\\Downloads\\prac.txt";
	  try {
          readFile(filename);
          System.out.println("File read successfully.");
      } catch (IOException e) {
          System.err.println("Error reading file: " + e.getMessage());
         
      }
  }
  public static void readFile(String filename) throws IOException {
	  FileInputStream fis = null;
	     InputStreamReader reader = null;

	        try {
	            fis = new FileInputStream(filename);
	            reader = new InputStreamReader(fis, StandardCharsets.UTF_8); // UTF-8 encoding

	            int character;
	            while ((character = reader.read()) != -1) {
	                System.out.print((char) character);
	            }

	        } catch (java.nio.file.NoSuchFileException e) {
	            throw new IOException("File not found: " + sourceFilename, e);
	        } finally { // Crucial: Close resources in reverse order in finally
	            if (reader != null) {
	                try {
	                    reader.close(); // This will also close fis
	                } catch (IOException e) {
	                    /* Ignore or log */
	                }
	            }
	            // No need to close fis separately since closing reader closes it
	        }
	    }
}
