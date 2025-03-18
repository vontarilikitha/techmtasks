package examples;
import static org.junit.Assert.assertEquals;

import org.junit.*;
import org.junit.Assert.*;
public class setuptest {
  private setup set;
  @BeforeClass
  public static void beforeall() {
	  System.out.println("Setting up shared resources before all tests...");
  }
  @Before
  public  void before() {
	  set=new setup();
	  System.out.println("setup before test case");
  }
  
  @Test
  public void testAddition() {
      int result = set.add(5, 3);
      assertEquals(8, result);
  }

  @Test
  public void testSubtraction() {
      int result = set.subtract(10, 4);
      assertEquals(6, result);
  }

  @Test
  public void testMultiplication() {
      int result = set.multiply(6, 2);
      assertEquals(12, result);
  }

  @Test
  public void testDivision() {
      int result = set.divide(8, 2);
      assertEquals(4, result);
  }

  @Test(expected = ArithmeticException.class)
  public void testDivideByZero() {
      set.divide(5, 0);
  }
  @After
  public void teardown() {
	  set=null;
	  System.out.println("Teardown after test case");
  }
  @AfterClass
  public static void afterAllTests() {
	  System.out.println("Cleaning up shared resources after all tests...");
  }
}
