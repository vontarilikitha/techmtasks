package Exceptionalhandling;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class File {
      public static void main(String [] args) {
    	  String file="C:\\Users\\keshi\\Downloads\\prac.txt";
    	  try {
    		  readFile(file);
    		  System.out.println("File read successfully");
    	  }
    	  catch(IOException e) {
    		  System.out.println("error reading file" +e.getMessage());
    	  }
      }
      public static void readFile(String filename)  throws IOException{
    	  try {
    		 Files.lines(Paths.get(filename)); 
    	  }
    	  catch(Exception e) {
    		  throw new IOException("File not found"+filename);
    	  }
      }
}
