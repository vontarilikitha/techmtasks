package coreproject1;
import java.io.*;
import java.util.Scanner;


public class ReverseString {

	public static void main(String[] args) {
		String s;
		Scanner sc = new Scanner(System.in);
		s= sc.nextLine();
		StringBuilder res = new StringBuilder();
		res.append(s);
		System.out.println("reverse string:"+res.reverse());
		res.reverse();
		
	}

}
