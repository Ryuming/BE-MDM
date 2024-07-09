package com.ticket.ticket.entities;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "tour")


public class Tour {
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId tourId;
    private String tourName;
    private LocalDateTime dateStart;
    private String signCar;
    private int numberCustomer;

}
