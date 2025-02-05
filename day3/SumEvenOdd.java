package collections1;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SumEvenOdd {
    public static <T extends Number> void sumEvenOdd(List<T> numbers) {
        int evenSum = 0, oddSum = 0;
        for (T num : numbers) {
            if (num.intValue() % 2 == 0) {
                evenSum += num.intValue();
            } else {
                oddSum += num.intValue();
            }
        }
        System.out.println("Sum of even numbers: " + evenSum);
        System.out.println("Sum of odd numbers: " + oddSum);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> numbers = new ArrayList<>();

        System.out.print("Enter number of elements: ");
        int n = scanner.nextInt();

        System.out.println("Enter numbers:");
        for (int i = 0; i < n; i++) {
            numbers.add(scanner.nextInt());
        }

        sumEvenOdd(numbers);
        scanner.close();
    }
}
