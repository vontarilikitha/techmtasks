package Multithreading;

public class EvenoeODD {
   public static void main(String [] args) {
	   EvenThread et=new EvenThread();
	   OddThread ot=new OddThread();
	   et.start();
	   ot.start();
   }

   public static class EvenThread extends Thread{
	   public void run() {
		   for(int i=0;i<=20;i++) {
			   if(i%2==0) {
				   System.out.println("even "+i);
			   }
		   }
	   }
   }
   public static class OddThread extends Thread{
	   public void run() {
		   for(int i=0;i<=20;i++) {
			   if(i%2!=0) {
				   System.out.println("odd "+i);
			   }
		   }
	   }
   }
}
