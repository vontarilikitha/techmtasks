package coreproject1;
import java.util.Scanner;


public class Capitalize {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String inputStr = sc.nextLine();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < inputStr.length(); i++) {
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(inputStr.charAt(i)));
            } else {
                result.append(Character.toLowerCase(inputStr.charAt(i)));
            }
        }

        System.out.println(result.toString());

	}

}

