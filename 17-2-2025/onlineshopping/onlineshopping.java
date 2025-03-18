package ex4;
import java.util.ArrayList;
import java.util.List;
public class onlineshopping implements Item{
	private String name;
	private double price ;
	public onlineshopping() {}
	public onlineshopping(String name ,Double price) {
		this.name=name;
		this.price=price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	   public String toString() {
	        return "Product{" +
	                "name='" + name + '\'' +
	                ", price=" + price +
	                '}';
	    }
}


