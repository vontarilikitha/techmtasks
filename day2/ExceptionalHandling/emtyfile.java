package Exceptionalhandling;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;

public class emtyfile {
    public static void main(String [] args) {
    	String filename="C:\\Users\\keshi\\Downloads\\prac.txt";
    	try {
    		checkempty(filename);
    		System.out.println("File is not empty");
    	}
    	catch(Exception e) {
    		System.out.println("error"+ " "+e.getMessage());
    		
    	}
    }
    public static void checkempty(String filename) throws IOException,Exception{
    	try {
    		long filesize=Files.size(Paths.get(filename));
    		if(filesize==0) {
    			throw new Exception("File is Empty"+filename);
    		}
    	}
    	catch(Exception e) {
    		throw new IOException("File not found" + e);
    	}
    }
}
