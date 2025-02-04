package coreproject1;

import java.util.Scanner;

public class Task2 {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number of students: ");
        int n = scanner.nextInt();
        scanner.nextLine();
        
        String[] names = new String[n];
        int[][] marks = new int[n][];
        int[] totals = new int[n];
        double[] averages = new double[n];
        
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name of student " + (i + 1) + ": ");
            names[i] = scanner.nextLine();
            
            System.out.print("Enter number of subjects: ");
            int subjects = scanner.nextInt();
            marks[i] = new int[subjects];
            
            System.out.println("Enter marks:");
            int total = 0;
            for (int j = 0; j < subjects; j++) {
                marks[i][j] = scanner.nextInt();
                total += marks[i][j];
            }
            scanner.nextLine();
            totals[i] = total;
            averages[i] = (double) total / subjects;
        }
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (totals[i] < totals[j]) {
                    int tempTotal = totals[i];
                    totals[i] = totals[j];
                    totals[j] = tempTotal;
                    
                    double tempAverage = averages[i];
                    averages[i] = averages[j];
                    averages[j] = tempAverage;
                    
                    String tempName = names[i];
                    names[i] = names[j];
                    names[j] = tempName;
                    
                    int[] tempMarks = marks[i];
                    marks[i] = marks[j];
                    marks[j] = tempMarks;
                }
            }
        }
        
        System.out.println("\nSorted Student List by Total Marks:");
        for (int i = 0; i < n; i++) {
            System.out.println("Name: " + names[i] + ", Total Marks: " + totals[i] + ", Average: " + averages[i]);
        }
        
        scanner.close();
    }

}
