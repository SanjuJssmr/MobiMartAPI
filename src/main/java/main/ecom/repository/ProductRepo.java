package main.ecom.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import main.ecom.model.Product;
import main.ecom.model.User;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
    Optional<Product> findByProdName(String prodName);
}
