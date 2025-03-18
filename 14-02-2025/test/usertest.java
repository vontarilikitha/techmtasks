package examples;

public class usertest {
  public static void main(String [] args) {
	  user u1=new user("a",12);
	  user u2=new user("b",25);
      assert u1.iseligile() : "Test failed: a should be eligible but isn't!";
      assert u2.iseligile() : "Test failed: b should be eligible but isn't!";

  }
}
