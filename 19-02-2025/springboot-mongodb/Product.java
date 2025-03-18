package com.app.springboot_jpa_mongodb.model;
import org.bson.types.ObjectId; 
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Product")
public class Product {
   @Id
   private ObjectId prodId;
   private String prodName;
   private double prodCost;
   public Product() {
	   super();
   }
   public Product(String prodName,double prodCost) {
	   this.prodName=prodName;
	   this.prodCost=prodCost;
   }
public Long getProdId() {
	return prodId;
}
public void setProdId(Long prodId) {
	this.prodId = prodId;
}
public String getProdName() {
	return prodName;
}
public void setProdName(String prodName) {
	this.prodName = prodName;
}
public double getProdCost() {
	return prodCost;
}
public void setProdCost(double prodCost) {
	this.prodCost = prodCost;
}
   
@Override
public String toString() {
	return "Product{"+"prodId="+ prodId+ ", prodName='" + prodName + '\'' +
            ", prodCost=" + prodCost +'}';
}
}
