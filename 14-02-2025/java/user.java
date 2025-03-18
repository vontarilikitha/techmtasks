package examples;

public class user {
  private String name;
  private int age;
  public user(String name,int age) {
	  this.name=name;
	  this.age=age;
  }
  public boolean iseligile() {
	  return age>=18;
  }
}
