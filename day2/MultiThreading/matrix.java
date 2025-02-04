package Multithreading;

public class matrix {
    static int [][] resultMatrix;
    static int [][] a,b;
    static int rowsa,colsa,colsb;
    public static void main(String [] args) {
    	a=new int [][] {
    		{1,2,3},
    		{4,5,6},
    		{7,8,9}};
    	b=new int [][] {
    		{1,2,3},
    		{4,5,6},
    		{7,8,9}};
    	
    
    rowsa=a.length;
    colsa=a[0].length;
    colsb=b[0].length;
    resultMatrix=new int[rowsa][colsb];
    Thread[] threads=new Thread[rowsa];
    for(int i=0;i<rowsa;i++) {
    	threads[i]=new Thread(new Multiplyrow(i));
    	threads[i].start();
    }
    for(int i=0;i<rowsa;i++) {
    	try {
    		threads[i].join();
    	}
    	catch(InterruptedException e) {
    		  e.printStackTrace();
    	}
    }
    System.out.println("Result Matrix:");
    printMatrix(resultMatrix);
    }
    static class Multiplyrow implements Runnable{
    	int row;
    	Multiplyrow(int row) {
            this.row = row;
        }
    	public void run() {
    		for(int j=0;j<colsa;j++) {
    			resultMatrix[row][j]=0;
    			for(int k=0;k<colsb;k++) {
    				resultMatrix[row][j]+=a[row][j]*b[k][j];
    			}
    		}
    	}
    }
    static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}
