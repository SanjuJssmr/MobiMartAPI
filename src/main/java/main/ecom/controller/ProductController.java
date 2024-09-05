package main.ecom.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import main.ecom.model.Product;
import main.ecom.service.ProductService;
import main.ecom.service.S3Service;

@RestController
@RequestMapping("/products")
public class ProductController {
    Map<String, Object> response = new HashMap<>();

    @Autowired
    private ProductService prodService;
    @Autowired
    private S3Service s3Service;

    @PostMapping("/createProduct")
    public ResponseEntity<Map<String, Object>> createNewProduct(@RequestPart MultipartFile file,
            @RequestPart Product prod) {
        Optional<Product> checkExist = prodService.getProdByName(prod.getProdName());
        if (checkExist.isPresent()) {
            response.put("status", 0);
            response.put("response", "Product already exist");

            return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
        }
        Product newProduct = prodService.saveProductr(prod);
        String fileUrl = s3Service.uploadFile(file, newProduct.get_id().toString());
        newProduct.setImage(fileUrl);
        prodService.saveProductr(newProduct);

        response.put("status", 1);
        response.put("response", "Product created Successfully");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
