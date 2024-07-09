package com.ticket.ticket.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClients;
import com.ticket.ticket.DTOs.TicketDTO;
import com.ticket.ticket.entities.PagedResponse;
import com.ticket.ticket.entities.Ticket;

@Service
public class TicketService {


    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    public PagedResponse<TicketDTO> ticketFilterHandler(
        String ticketId,
        String ticketTourId,
        String ticketTourName,
        String userId,
        LocalDateTime dateStartBegin,
        LocalDateTime dateStartEnd,
        int page,
        int size
        
    )
    {
        
        
        List<Ticket> tickets = new ArrayList<Ticket>();
        MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create("mongodb+srv://huynhtrongnghia10012000:18120478@ryucluster.hkwulwu.mongodb.net/?retryWrites=true&w=majority&appName=RyuCluster"), "MDM");
        Query query = new Query();
        if (!ticketId.isBlank() && ticketId !=null)
        {
            ObjectId ticketObjectId = new ObjectId(ticketId);
            query.addCriteria(Criteria.where("ticketId").is(ticketObjectId));


        }


        if (!ticketTourId.isBlank() && ticketTourId!=null)
        {
            query.addCriteria(Criteria.where("ticketTourId").is(ticketTourId));
 
        }

        if (!ticketTourName.isBlank() && ticketTourName!=null)
        {
            query.addCriteria(Criteria.where("ticketTourName").regex(".*"+ticketTourName+".*"));


        }

        
        if (!userId.isBlank() && userId!=null)
        {
            query.addCriteria(Criteria.where("userId").is(userId));
          
        }


        if (dateStartBegin != null && dateStartEnd!=null)
        {
            query.addCriteria(Criteria.where("dateStart").gte(dateStartBegin).lte(dateStartEnd));
            
        }

        long totalItems = mongoTemplate.count(query, Ticket.class);

        Pageable pageable = PageRequest.of(page, size);
        query.with(pageable);

        logger.info("Mongo Query: {}", query);
        tickets = mongoTemplate.find(query, Ticket.class);
        logger.info("Events found: {}", tickets.size());

        

        List<TicketDTO> ticketDTOs = new DTOHandler().convertTicketToDTO(tickets);

        int totalPages = (int) Math.ceil((double) totalItems / size);

        return new PagedResponse<>(totalItems, totalPages, page, ticketDTOs);
    }


    public List<TicketDTO> convertTicketToDTO(List<Ticket> tickets)
    {
        List<TicketDTO> ticketDTOs = tickets.stream().map(ticket-> {
            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO.setTicketId(ticket.getTicketId().toString());
            ticketDTO.setTicketTourId(ticket.getTicketTourId());
            ticketDTO.setTicketTourName(ticket.getTicketTourName());
            ticketDTO.setTicketDescription(ticket.getTicketDescription());
            ticketDTO.setTicketPrice(ticket.getTicketPrice());
            ticketDTO.setUserName(ticket.getUserId());
            ticketDTO.setTimeCreated(ticket.getTimeCreated());
            ticketDTO.setDateStart(ticket.getDateStart());

            return ticketDTO;
        }).collect(Collectors.toList());
        return ticketDTOs;
    }
}
