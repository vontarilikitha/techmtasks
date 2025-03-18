package examples;

public class TimeLimit {
        public static void slowmethod() {
        	try {
        		Thread.sleep(1500);
        	}
        	catch(InterruptedException e) {
        		System.out.println(e);
        	}
        }
}
