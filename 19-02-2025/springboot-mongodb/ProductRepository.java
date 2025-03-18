package com.app.springboot_jpa_mongodb.repo;



import org.springframework.data.mongodb.repository.MongoRepository; 
import org.springframework.stereotype.Repository;
import com.app.springboot_jpa_mongodb.model.Product;
@Repository
public interface ProductRepository extends MongoRepository <Product,Long>{

}
