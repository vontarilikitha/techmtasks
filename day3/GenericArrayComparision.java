package collections1;

import java.util.Arrays;
import java.util.Scanner;

public class GenericArrayComparision {
    public static <T> boolean areArraysEqual(T[] array1, T[] array2) {
        return Arrays.equals(array1, array2);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter size of arrays: ");
        int size = scanner.nextInt();
        
        Integer[] arr1 = new Integer[size];
        Integer[] arr2 = new Integer[size];

        System.out.println("Enter elements for first array:");
        for (int i = 0; i < size; i++) {
            arr1[i] = scanner.nextInt();
        }

        System.out.println("Enter elements for second array:");
        for (int i = 0; i < size; i++) {
            arr2[i] = scanner.nextInt();
        }

        System.out.println("Arrays are equal: " + areArraysEqual(arr1, arr2));
        scanner.close();
    }
}
