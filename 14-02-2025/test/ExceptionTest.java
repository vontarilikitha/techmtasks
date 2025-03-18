package examples;
import org.junit.Test;
import examples.exception;
import junit.framework.Assert;
public class ExceptionTest {
@Test(expected=IndexOutOfBoundsException.class)
public  void testmy() {
      exception m=new exception();
      int index=10;
      
    	 m.my(10); 
   
      
}
@Test(expected=ArithmeticException.class)
	public void test2() {
		exception m=new exception();
		int num=10;
		int den=0;
		m.my(num,den);
	}
}

