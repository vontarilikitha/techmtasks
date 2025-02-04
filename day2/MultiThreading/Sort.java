package Multithreading;
import java.util.Arrays;

class ParallelMergeSort extends Thread {
    private int[] array;
    private int left, right;

    public ParallelMergeSort(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        if (left < right) {
            int mid = left + (right - left) / 2;

            
            ParallelMergeSort leftSorter = new ParallelMergeSort(array, left, mid);
            ParallelMergeSort rightSorter = new ParallelMergeSort(array, mid + 1, right);

            leftSorter.start();
            rightSorter.start();

            try {
                leftSorter.join();  
                rightSorter.join(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            
            merge(array, left, mid, right);
        }
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

  
        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, mid + 1, rightArray, 0, n2);

        int i = 0, j = 0, k = left;


        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

      
        while (i < n1) {
            array[k++] = leftArray[i++];
        }

      
        while (j < n2) {
            array[k++] = rightArray[j++];
        }
    }

    public static void main(String[] args) {
        int[] array = {9, 5, 1, 4, 3, 8, 6, 2, 7};

        System.out.println("Original Array: " + Arrays.toString(array));

        ParallelMergeSort sorter = new ParallelMergeSort(array, 0, array.length - 1);
        sorter.start();

        try {
            sorter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Sorted Array: " + Arrays.toString(array));
    }
}
