package coreproject1;
import java.util.Arrays;


public class ArrayCopyex {

	public static void main(String[] args) {
		 int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	        
	        int newSize = (a.length + 1) / 2;
	        int[] b = new int[newSize];

	        // Copy alternate elements using arraycopy
	        for (int i = 0, j = 0; i < a.length; i += 2, j++) {
	            System.arraycopy(a, i, b, j, 1);
	        }

	        // Print the destination array
	        System.out.println("Alternative Elements: " + Arrays.toString(b));


	}

}
