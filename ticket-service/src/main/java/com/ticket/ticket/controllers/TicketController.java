package com.ticket.ticket.controllers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.events.Event;

import com.thoughtworks.xstream.converters.time.LocalDateTimeConverter;
import com.ticket.ticket.DTOs.TicketDTO;
import com.ticket.ticket.entities.PagedResponse;
import com.ticket.ticket.entities.Ticket;
import com.ticket.ticket.repositories.TicketRepository;
import com.ticket.ticket.services.DTOHandler;
import com.ticket.ticket.services.TicketService;

@RestController
@RequestMapping(value = "/api/ticket-service")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;


    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "/ticket")
    public ResponseEntity<PagedResponse<TicketDTO>> getAllTicket()
    {
        try {
            PagedResponse<TicketDTO> tickets = ticketService.ticketFilterHandler("", "", "", "", LocalDateTime.parse("1800-01-13T03:09:42.411"), LocalDateTime.parse("2400-01-13T03:09:42.411"), 0, 12);
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping(value = "/ticket/search")
    public ResponseEntity<PagedResponse<TicketDTO>> getTicketByFilter(
        @RequestParam(name = "ticketId", required = false) String ticketId,
        @RequestParam(name = "ticketTourId", required = false) String ticketTourId,
        @RequestParam(name = "ticketTourName", required = false) String ticketTourName,
        @RequestParam(name = "userId", required = false) String userId,
        @RequestParam(name = "dateStartBegin", defaultValue = "1800-01-13T03:09:42.411") @DateTimeFormat LocalDateTime dateStartBegin,
        @RequestParam(name = "dateStartEnd", defaultValue = "2400-01-13T03:09:42.411") @DateTimeFormat LocalDateTime dateStartEnd,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "12") int size
    )
    {
        try {
            PagedResponse<TicketDTO> tickets = ticketService.ticketFilterHandler(
                ticketId,
                ticketTourId, 
                ticketTourName, 
                userId, 
                dateStartBegin, 
                dateStartEnd, 
                page, 
                size);
            if (tickets.getTotalItems()==0)
            {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            else 
            {
                return new ResponseEntity<>(tickets, HttpStatus.OK);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/ticket/getByTicketId")
    public ResponseEntity<TicketDTO> getTicketByUserId(
        @RequestParam(name = "ticketId") String ticketId
    )
    {
        try {
            Optional<Ticket> ticket = ticketRepository.findById(ticketId);
            return new ResponseEntity<>(new DTOHandler().convertTicketToDTO(ticket.get()), HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/ticket")
    public ResponseEntity<TicketDTO> createTicket(
        @RequestBody Ticket ticket
    )
    {
        try {
            ticket.setTimeCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
            Ticket _ticket = ticketRepository.save(ticket);
            return new ResponseEntity<>(new DTOHandler().convertTicketToDTO(_ticket), HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/ticket")
    public ResponseEntity<TicketDTO> updateTicket(
        @RequestBody Ticket ticket
        
    )
    {
        Optional<Ticket> ticketData = ticketRepository.findById(ticket.getTicketId().toString());
        if (ticketData.isPresent())
        {
            Ticket _ticket = ticketData.get();
            _ticket.setTicketId(ticket.getTicketId());
            _ticket.setTicketTourId(ticket.getTicketTourId());
            _ticket.setTicketTourName(ticket.getTicketTourName());
            _ticket.setTicketDescription(ticket.getTicketDescription());
            _ticket.setTicketPrice(ticket.getTicketPrice());
            _ticket.setUserId(ticket.getUserId());
            _ticket.setTimeCreated(ticket.getTimeCreated());
            _ticket.setDateStart(ticket.getDateStart());
            Ticket returnedTicket = ticketRepository.save(_ticket);

            return new ResponseEntity<>(new DTOHandler().convertTicketToDTO(returnedTicket), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "/ticket")
    public ResponseEntity<HttpStatus> deleteTicketById(
        @RequestParam(name = "ticketId") String ticketId
    )
    {
        try {
            ticketRepository.deleteById(ticketId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

