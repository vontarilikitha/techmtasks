package ex3;

public class inventory {
  private String name;
  private int quantity;
  private double price;
  public inventory(String name,int quantity,double price) {
	  this.name=name;
	  this.quantity=quantity;
	  this.price=price;
  }
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public String toString() {
    return "Inventory{" +
            "productName='" + name + '\'' +
            ", quantity=" + quantity +
            ", price=" + price +
            '}';
} 
}
