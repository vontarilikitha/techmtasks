package Files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileLineByLine {

    public static void main(String[] args) {

        String filePath = "C:\\Users\\keshi\\Downloads\\prac.txt"; 

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { 

            String line;
            int lineNumber = 1; 

            while ((line = br.readLine()) != null) {
                System.out.println("Line " + lineNumber + ": " + line); 
                lineNumber++; 
            }

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
}