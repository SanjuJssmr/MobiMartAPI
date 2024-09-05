package main.ecom.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;
import main.ecom.model.Product;
import main.ecom.repository.ProductRepo;


@Service
public class ProductService {
    
    @Autowired
    private ProductRepo prodDb;

    public List<Product> getAllProduct() {
        return prodDb.findAll();
    }

    public Optional<Product> getProdById(ObjectId id) {
        return prodDb.findById(id);
    }

    public Optional<Product> getProdByName(String prodName) {
        return prodDb.findByProdName(prodName);
    }

    public Product saveProductr(Product obj){
        return prodDb.save(obj);
    }
}
