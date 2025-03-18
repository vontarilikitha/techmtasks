package ex3;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Main {
	public static void main(String [] args) {
ApplicationContext con=new ClassPathXmlApplicationContext("config.xml");
inventory inve=con.getBean("inv",inventory.class);
System.out.println(inve);
	}
}
