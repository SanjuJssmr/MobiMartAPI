package main.ecom.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import main.ecom.model.Product;
import main.ecom.repository.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo prodDb;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Product> getAllProduct() {
        return prodDb.findAll();
    }

    public Optional<Product> getProdById(ObjectId id) {
        return prodDb.findById(id);
    }

    public Optional<Product> getProdByName(String prodName) {
        return prodDb.findByProdName(prodName);
    }

    public Product saveProductr(Product obj) {
        return prodDb.save(obj);
    }

     public void updateMultipleFields(String prodId, Map<String, Object> fieldsToUpdate) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(prodId)));

        Update update = new Update();
        fieldsToUpdate.forEach(update::set);

        mongoTemplate.updateMulti(query, update, "product");
    }
}
