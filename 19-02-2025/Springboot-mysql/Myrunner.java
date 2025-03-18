package com.app.springboot_jpa_mysql.runner;
import java.util.Scanner;
import com.app.springboot_jpa_mysql.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.app.springboot_jpa_mysql.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
@Component
public class Myrunner implements CommandLineRunner {
@Autowired	
private ProductRepository product;

@Override
@Transactional
public void run(String...args) throws Exception{
	Scanner sc=new Scanner(System.in);
	int choice=-1;
	do {
		 System.out.println("\nProduct Management Menu:");
         System.out.println("1. Insert Product");
         System.out.println("2. Delete Product");
         System.out.println("3. Update Product");
         System.out.println("4. View All Products");
         System.out.println("0. Exit");
         System.out.print("Enter your choice: ");
         try {
        	 choice=sc.nextInt();
        	 switch(choice) {
        	 case 1:insertproduct();
        	        break;
        	 case 2:deleteproduct();
        	 break;
        	 case 3:updateproduct();
        	 break;
        	 case 4:
        		 viewAllProducts();
        		 break;
        	 case 0:
        		 System.out.println("Exiting...");
        		 break;
        	 default:
        		 System.out.println("Invalid choice. Please try again.");
        	 }
         }
         catch(Exception e) {
        	 System.out.println(e);
         }
	}while(choice!=0);
	sc.close();
}	
	
public void insertproduct() {
		Scanner sc=new Scanner(System.in);
	System.out.println("Enter product name (or type 'exit'):");
    String productName = sc.nextLine();
 
    	try {
    		System.out.println("Enter product cost:");
            double productCost = sc.nextDouble();
            sc.nextLine();
            if(productCost<0) {
            	System.out.println("cost cant be negative");
            }
            Product prod=new Product(productName,productCost);
            product.save(prod);
            System.out.println("Product inserted: " + product);
            
            
    	}
    	catch(Exception e) {
    		System.out.println(e);
    		
    	}

}
public void deleteproduct() {
	Scanner sc=new Scanner(System.in);
	System.out.print("Enter product ID to delete: ");
	try {
		Long id=sc.nextLong();
		sc.nextLine();
		if(product.existsById(id)) {
			product.deleteById(id);
			System.out.println("Product deleted.");
		}
		else {
            System.out.println("Product with ID " + id + " not found.");
        }
	}
	catch(Exception e) {
		System.out.println(e);
	}
}

public void updateproduct() {
	Scanner sc=new Scanner(System.in);
	try {
		Long id=sc.nextLong();
		sc.nextLine();
		if(product.existsById(id)) {
			Product pro=product.findById(id).orElse(null);
			if(pro!=null) {
				System.out.print("Enter new product name (or press Enter to keep current): ");
                String newName = sc.nextLine();
                if (!newName.isEmpty()) {
                    pro.setProdName(newName);
                }
                System.out.print("Enter new product cost (or press Enter to keep current): ");
                if(sc.hasNextDouble()) {
                	double newCost=sc.nextDouble();
                	sc.nextLine();
                	if(newCost>=0) {
                		 pro.setProdCost(newCost);
                	}
                	else {
                		System.out.println("Cost can't be negative.");
                		return;
                	}
                }
                else {
                	 sc.nextLine();
                }
                product.save(pro);
                System.out.println("Product updated: " + product);
			}
			
			
		}else {
			System.out.println("Product with ID " + id + " not found.");
		}
		
	}
	catch(Exception e) {
		System.out.println(e);
	}
}





private void viewAllProducts() {
    System.out.println("All Products:");
    product.findAll().forEach(System.out::println);
}




}
