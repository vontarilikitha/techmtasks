package examples;

public class exception {
     public void my(int index) {
    	 int arr []= {1,2,3,4,6,7};
    	 if(index<0 || index>=arr.length) {
    		 throw new IndexOutOfBoundsException("Index" + index + "out of bounds for length" );
    	 }
     }
     public int my(int num,int den) {
    	 if(den==0) {
    		 throw new ArithmeticException("Division by zero");
    	 }
    	 return num/den;
     }
}
