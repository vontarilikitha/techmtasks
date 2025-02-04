package coreproject1;
import java.util.Arrays;

public class EqualsVsDeepEquals {

	public static void main(String[] args) {
		int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2, 3};
        int[] arr3 = arr1;
        System.out.println("arr1.equals(arr2): " + arr1.equals(arr2));
        System.out.println("arr1.equals(arr3): " + arr1.equals(arr3));
        System.out.println("Arrays.deepEquals(new Object[]{arr1}, new Object[]{arr2})): " 
                + Arrays.deepEquals(new Object[]{arr1}, new Object[]{arr2}));

	}

}



