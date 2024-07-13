package com.ticket.ticket.DTOs;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

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
@RedisHash("tour")

public class TourDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId tourId;
    private String tourName;
    private LocalDateTime dateStart;
    private String signCar;
    private int numberCustomer;
    private int seatLeft;
}
