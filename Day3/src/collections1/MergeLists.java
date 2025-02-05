package collections1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MergeLists {
    public static <T> List<T> mergeLists(List<T> list1, List<T> list2) {
        List<T> mergedList = new ArrayList<>();
        int maxSize = Math.max(list1.size(), list2.size());

        for (int i = 0; i < maxSize; i++) {
            if (i < list1.size()) mergedList.add(list1.get(i));
            if (i < list2.size()) mergedList.add(list2.get(i));
        }
        return mergedList;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        System.out.print("Enter size of first list: ");
        int size1 = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter elements for first list:");
        for (int i = 0; i < size1; i++) {
            list1.add(scanner.nextLine());
        }

        System.out.print("Enter size of second list: ");
        int size2 = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter elements for second list:");
        for (int i = 0; i < size2; i++) {
            list2.add(scanner.nextLine());
        }

        List<String> mergedList = mergeLists(list1, list2);
        System.out.println("Merged List: " + mergedList);
        scanner.close();
    }
}
