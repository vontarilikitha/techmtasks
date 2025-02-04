package Multithreading;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
public class SumPrimes {
     private static final int nt=4;
     private static AtomicLong sum=new AtomicLong(0);
     public static void main(String [] args) throws InterruptedException{
    	 int limit=1000000;
    	 ExecutorService executor =Executors.newFixedThreadPool(nt);
    	 int chunksize=limit/nt;
    	 for(int i=0;i<nt;i++) {
    		 int start=i*chunksize+1;
    		 int end=(i==nt-1)? limit:(i+1)*chunksize;
    		 executor.execute(new ThreadO(start,end));
    	 }
    	 executor.shutdown();
    	 executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    	 System.out.println("Sum of prime numbers up to " + limit + " is: " + sum.get());
     }
     static class ThreadO implements Runnable{
    	 private final int  start, end;
    	 public ThreadO(int start,int end) {
    		 this.start=start;
    		 this.end=end;
    	 }
    	 public void run() {
    		 long lsum=0;
    		 for(int i=start;i<=end;i++) {
    			 if(isprime(i)) {
    				 lsum=lsum+i;
    			 }
    		 }
    		 sum.addAndGet(lsum);
    		 
    	 }
    	 private boolean isprime(int num) {
    		 if(num<2) return false;
    		 if(num==2) return true;
    		 if(num%2==0) return false;
    		 for(int i=3;i*i <=num;i++) {
    			 if(num%i==0) return false;
    		 }
    		 return true;
    	 }
     }
}
