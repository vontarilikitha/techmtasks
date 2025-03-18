package examples;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;  
import java.util.Arrays;
import java.util.Collection;
import examples.Parametrize;
import org.junit.Test;
import org.junit.runners.Parameterized;
@RunWith(Parameterized.class)
public class Parametrizedtest {
       private int input;
       private boolean expectedoutput;
       public Parametrizedtest(int input,boolean expectedoutput) {
    	   this.input=input;
    	   this.expectedoutput=expectedoutput;
       }
       @Parameterized.Parameters
       public static Collection<Object[]> testData(){
    	   return Arrays.asList(new Object[][]{
    		   {2, true},  
               {3, false},  
               {10, true},  
               {15, false}, 
               {0, true} 
    	   });
       }
       @Test
       public void testiseven() {
    	   assertEquals(expectedoutput,Parametrize.isEven(input));
       }
       
}
