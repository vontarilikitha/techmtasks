package examples;
import org.junit.Test;
import static org.junit.Assert.*;
public class Timelimittest {
   @Test(timeout=1000)
   public void testslow() {
	   TimeLimit.slowmethod();
   }
}
