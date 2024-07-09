package com.ticket.ticket.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ticket.ticket.entities.Tour;

public interface TourRepository extends MongoRepository<Tour, String>{
    
}
