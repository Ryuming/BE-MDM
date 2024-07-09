package com.ticket.ticket.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ticket.ticket.DTOs.TicketDTO;
import com.ticket.ticket.entities.Ticket;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String>{

}
