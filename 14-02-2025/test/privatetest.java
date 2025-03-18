package examples;
import org.junit.Test;
import static org.junit.Assert.*;
public class privatetest {
    @Test
    public void testsquareplus(int num) {
    	privateex p=new privateex();
    	assertEquals(26,p.squareoneplus(5));
    }

    
}
