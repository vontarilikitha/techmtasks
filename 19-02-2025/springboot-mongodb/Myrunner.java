package com.app.springboot_jpa_mongodb.runner;
import org.bson.types.ObjectId; 
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.app.springboot_jpa_mongodb.model.Product;
import com.app.springboot_jpa_mongodb.repo.ProductRepository;

@Component
public class Myrunner implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository; // Correct name

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        do {
            // ... (Menu and input handling - same as before)

            switch (choice) {
                case 1:
                    insertProduct(sc);
                    break;
                case 2:
                    deleteProduct(sc);
                    break;
                case 3:
                    updateProduct(sc);
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
        } while (choice != 0);

        sc.close();
    }

    private void insertProduct(Scanner sc) {
        System.out.print("Enter product name: ");
        String productName = sc.nextLine();

        System.out.print("Enter product price: ");
        try {
            double productPrice = sc.nextDouble();
            sc.nextLine(); // Consume newline

            if (productPrice < 0) {
                System.out.println("Price can't be negative");
                return;
            }

            Product prod = new Product(productName, productPrice);
            productRepository.save(prod); // Save the product
            System.out.println("Product inserted: " + prod);

        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid price input.");
            sc.nextLine();
        }
    }

   

    private void updateProduct(Scanner sc) {
        System.out.print("Enter product ID to update: ");
        String id = sc.nextLine(); // Read ID as String

        java.util.Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            System.out.print("Enter new product name (or press Enter to keep current): ");
            String newName = sc.nextLine();
            if (!newName.isEmpty()) {
                product.setProdName(newName); // Use setName
            }

            System.out.print("Enter new product price (or press Enter to keep current): ");
            if (sc.hasNextDouble()) {
                double newPrice = sc.nextDouble();
                sc.nextLine();
                if (newPrice >= 0) {
                    product.setProdCost(newPrice); // Use setPrice
                } else {
                    System.out.println("Cost can't be negative.");
                    return;
                }
            } else {
                sc.nextLine();
            }

            productRepository.save(product);
            System.out.println("Product updated: " + product);
        } else {
            System.out.println("Product with ID " + id + " not found.");
        }
    }

    private void viewAllProducts() {
        System.out.println("All Products:");
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            products.forEach(System.out::println);
        }
    }
}