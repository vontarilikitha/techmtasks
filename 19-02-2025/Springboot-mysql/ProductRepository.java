package com.app.springboot_jpa_mysql.repo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import com.app.springboot_jpa_mysql.model.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

}
