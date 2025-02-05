package collections1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ReverseList {
    public static <T> List<T> reverseList(List<T> list) {
        List<T> reversedList = new ArrayList<>(list);
        Collections.reverse(reversedList);
        return reversedList;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> elements = new ArrayList<>();

        System.out.print("Enter number of elements: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter elements:");
        for (int i = 0; i < n; i++) {
            elements.add(scanner.nextLine());
        }

        List<String> reversed = reverseList(elements);
        System.out.println("Reversed list: " + reversed);
        scanner.close();
    }
}