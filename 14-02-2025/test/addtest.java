package examples;
import junit.framework.Assert;
import org.junit.Test;
import examples.add;
public class addtest {
@Test
public void testadd() {
	add a=new add();
	Assert.assertEquals(4, a.add1(2, 2));
}
}
