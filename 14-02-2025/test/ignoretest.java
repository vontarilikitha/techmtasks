package examples;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Ignore;
public class ignoretest {
  @Test
  public void testadd() {
	  assertEquals(5,ignoreee.add(2, 3));
  }
  @Ignore
  @Test
  public void testifnore() {
	  assertEquals(10,ignoreee.add(5, 5));
  }
}
