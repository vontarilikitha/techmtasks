package Exceptionalhandling;

public class Odd {
	
  public static void main(String [] args) {
	  try {
		  check(10);
		  check(5);
	  }
	  catch(IllegalArgumentException e) {
		  System.out.println(e.getMessage());
	  }
	
  }
  public static void check(int num) throws IllegalArgumentException{
	  if(num%2!=0) {
		  throw new IllegalArgumentException("you shpuld give even num");
		  
	  }
	  else {
		  System.out.println(num + " is an even number.");
	  }
  }
}
