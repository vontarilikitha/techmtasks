package Files;

import java.util.Scanner;

public class console {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Enter your name: ");
        String name = input.nextLine();

        System.out.println("Enter your age: ");
        int age = input.nextInt();
        input.nextLine();

        System.out.println("Enter your favorite decimal number: ");
        double favoriteNumber = input.nextDouble();

        System.out.println("Enter a single character: ");
        char singleCharacter = input.next().charAt(0);

        System.out.println("Enter a word: ");
        String word = input.next();

        System.out.println("Hello, " + name + "!");
        System.out.println("You are " + age + " years old.");
        System.out.println("Your favorite decimal number is: " + favoriteNumber);
        System.out.println("Your single character is: " + singleCharacter);
        System.out.println("Your word is " + word);

        input.close();
    }
}