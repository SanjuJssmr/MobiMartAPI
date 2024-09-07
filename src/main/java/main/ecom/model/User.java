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
public class User {
    @Id
    private ObjectId _id;
    private String userName;
    private String password;
    private String email;
    private String otp;
    private String age;
    private String city;
    private String state;
    private Integer status = 0;
    private Integer role = 1;
    private int[] card = {};
    @CreatedDate
    @Field("createdAt")
    private Instant createdAt;
    @LastModifiedDate
    @Field("updatedAt")
    private Instant updatedAt;
}
