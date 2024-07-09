package com.ticket.ticket.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClients;
import com.ticket.ticket.DTOs.TourDTO;
import com.ticket.ticket.entities.PagedResponse;
import com.ticket.ticket.entities.Ticket;
import com.ticket.ticket.entities.Tour;

@Service
public class TourService {
     private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

     public PagedResponse<TourDTO> tourFilterHeandler(
        String tourId,
        String tourName,
        LocalDateTime dateStartBegin,
        LocalDateTime dateStartEnd,
        String signCar,
        int page,
        int size

     )
     {
        List<Tour> tours = new ArrayList<Tour>();
        MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create("mongodb+srv://huynhtrongnghia10012000:18120478@ryucluster.hkwulwu.mongodb.net/?retryWrites=true&w=majority&appName=RyuCluster"), "MDM");
        Query query = new Query();
        if (!tourId.isBlank() && tourId!=null)
        {
            ObjectId tourObjectId = new ObjectId(tourId);
            query.addCriteria(Criteria.where("tourId").is(tourObjectId));
        }

        if (!tourName.isBlank() && tourName != null)
        {
            query.addCriteria(Criteria.where("tourName").regex(".*"+ tourName +".*"));
        }

        if (dateStartBegin!=null && dateStartEnd!=null)
        {
            query.addCriteria(Criteria.where("dateStart").gte(dateStartBegin).lte(dateStartEnd));
        }

        if (!signCar.isBlank() && signCar!=null)
        {
            query.addCriteria(Criteria.where(signCar).is(signCar));
        }
        
        long totalItems = mongoTemplate.count(query, Tour.class);
        Pageable pageable = PageRequest.of(page, size);
        query.with(pageable);

        logger.info("Mongo Query: {}", query);
        tours = mongoTemplate.find(query, Tour.class);
        logger.info("Events found: {}", tours.size());

        List<TourDTO> tourDTOs = new DTOHandler().convertTourToDTO(tours);

        int totalPages = (int) Math.ceil((double) totalItems / size);

        return new PagedResponse<>(totalItems, totalPages, page, tourDTOs);

     }
}
