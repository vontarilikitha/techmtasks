package collections1;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindIndex {
    public static <T> int findFirstOccurrence(List<T> list, T target) {
        return list.indexOf(target);
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

        System.out.print("Enter element to search: ");
        String target = scanner.nextLine();

        int index = findFirstOccurrence(elements, target);
        System.out.println("Index of first occurrence: " + index);
        scanner.close();
    }
}

