package Files;

import java.io.File;
import java.text.DecimalFormat;

public class FileSizeConverter {

    public static void main(String[] args) {

        String filePath = "C:\\Users\\keshi\\Downloads\\prac.txt";
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        long fileSizeInBytes = file.length();

        System.out.println("File size in bytes: " + fileSizeInBytes + " bytes");
        System.out.println("File size in KB: " + formatSize(fileSizeInBytes, "KB"));
        System.out.println("File size in MB: " + formatSize(fileSizeInBytes, "MB"));
        System.out.println("File size in GB: " + formatSize(fileSizeInBytes, "GB")); // Added GB


    }

    private static String formatSize(long bytes, String unit) {
        double size = bytes;


        if (unit.equals("KB")) {
            size /= 1024;
        } else if (unit.equals("MB")) {
            size /= (1024 * 1024);
        } else if (unit.equals("GB")) {
            size /= (1024 * 1024 * 1024);
        }
        DecimalFormat df = new DecimalFormat("0.##"); // Format to two decimal places
        return df.format(size) + " " + unit;
    }
}}
