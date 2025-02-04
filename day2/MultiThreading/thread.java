package Multithreading;

public class thread  extends Thread{
public static void main(String [] args) {
	thread t=new thread();
	t.start();
	System.out.println("main thread");
}
public void run() {
	
	System.out.println("thread 1");
}
}
