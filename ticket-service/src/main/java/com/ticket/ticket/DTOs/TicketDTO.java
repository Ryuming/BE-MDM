package com.ticket.ticket.DTOs;

import java.time.LocalDateTime;

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

public class TicketDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private String ticketId;
    private String ticketTourId;
    private String ticketTourName;
    private String ticketDescription;
    private Float ticketPrice;
    private String userName;
    private LocalDateTime timeCreated;
    private LocalDateTime dateStart;
    
}
