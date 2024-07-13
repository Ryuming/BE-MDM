package com.ticket.user.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCrypt;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "user")
public class User {


    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId userId;
    private String phoneNumber;
    private String residentName;
    private String residentId;
    private String password;
    private String role;
    
}