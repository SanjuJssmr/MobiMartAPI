package main.ecom.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import main.ecom.model.Product;
@Repository
public interface ProductRepo extends MongoRepository<Product, ObjectId> {
    Optional<Product> findByProdName(String prodName);
}
