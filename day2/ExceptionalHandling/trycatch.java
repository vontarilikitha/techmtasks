package Exceptionalhandling;

public class trycatch {
      public static void main(String[] args) {
    	  try {
    		  int res=8/0;
    		  
    	  }
    	  catch(Exception e) {
    		  System.out.println(e);
    		  }
    	  finally {
    		  System.out.println("Finally Block excuted");
    		  
    	  }
    	  System.out.println("Normal Excution");
      }
}
