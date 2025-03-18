package ex4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Main {
public static void main(String [] args) {
	  ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	  ShoppingCart cart = context.getBean("shoppingcart", ShoppingCart.class);
	  Item laptop = context.getBean("lap", Item.class);
      Item mouse = context.getBean("mouse", Item.class);
      cart.addItem(laptop); 
      cart.addItem(mouse);
      System.out.println(cart);

      double total = cart.calculateTotal();
      System.out.println("Total: $" + total);
}
}
