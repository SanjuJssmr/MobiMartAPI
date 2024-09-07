package main.ecom.model;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {
    @Id
    private ObjectId _id;
    private String prodName;
    private String prodType;
    private Integer price;
    private Integer quantity;
    private String brand;
    private Integer status = 1;
    private String image;
    @CreatedDate
    @Field("createdAt")
    private Instant createdAt;
    @LastModifiedDate
    @Field("updatedAt")
    private Instant updatedAt;
}
