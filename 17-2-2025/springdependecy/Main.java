package springex;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Main {
 public static void main(String [] args) {
	 ApplicationContext context=new ClassPathXmlApplicationContext("config.xml");
	 Student st=context.getBean("student",Student.class);
	 st.setgrade("A+");
     System.out.println(st);
 }
}
