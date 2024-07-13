package com.ticket.user.DTOs;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class UserDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId userId;
    private String phoneNumber;
    private String residentName;
    private String residentId;
    private String role;
}
