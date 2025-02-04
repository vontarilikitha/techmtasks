package Exceptionalhandling;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
public class negative {
	public static void main(String[] args) {
        String filename = "numbers.txt";

        try {
            checkNegativeNumbers(filename);
            System.out.println("All numbers are negative.");
        } catch (IOException  e) {
            System.err.println("Error: " + e.getMessage());
             
        }
    }
	public static void checkNegativeNumbers(String filename) throws IOException, Exception {
       
	}
}
