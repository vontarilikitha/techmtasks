package practice;
import java.util.Scanner;
public class students {
	public static void main(String [] args) {
	Scanner sc=new Scanner(System.in);
	 int n=sc.nextInt();
    String [] arr=new String[n];
    int marks [][]=new int[n][5];
       for(int i=0;i<n;i++) {
    	   System.out.println("enter Student name");
    	   arr[i]=sc.next();
    	   int sum=0;
    	   System.out.println("enter marks");
    	   for(int j=0;j<3;j++) {
    		   
    		   marks[i][j]=sc.nextInt();
    		   sum=sum+marks[i][j];
    	   }
    	   marks[i][3] = sum;
           marks[i][4] = sum / 3;
       }
       int k=0;
       for(int i=0;i<marks.length;i++) {
    	   System.out.print(arr[k]+" ");
    	   for(int j=0;j<marks[i].length;j++) {
    		  
    		   System.out.print(marks[i][j]+" ");
    	   }
    	   k++;
    	   System.out.println();
       }
       
}
}
