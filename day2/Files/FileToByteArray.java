package Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
public class FileToByteArray {
	public static void main(String[] args) {

        String filePath = "C:\\Users\\keshi\\Downloads\\prac.txt"; 

        try {
            File file = new File(filePath);

           
            if (!file.exists()) {
                System.err.println("File not found: " + filePath);
                return; 
            }
            
           
            if (!file.isFile()) {
                System.err.println("Not a file: " + filePath);
                return;
            }

           
            long fileSize = file.length();

            byte[] fileBytes = new byte[(int) fileSize]; // Cast to int is safe since file size is usually within int range

           
            FileInputStream fis = new FileInputStream(file);

            
            int bytesRead = fis.read(fileBytes);

            
            if (bytesRead != fileSize) {
                System.err.println("Error: Could not read the entire file. Bytes read: " + bytesRead + ", Expected: " + fileSize);
            }


            
            fis.close();


            
            System.out.println("File contents as byte array:");
            
            int bytesToPrint = Math.min(20, fileBytes.length); // Print max 20 bytes.
            System.out.println(Arrays.toString(Arrays.copyOfRange(fileBytes, 0, bytesToPrint)));

           
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
}
