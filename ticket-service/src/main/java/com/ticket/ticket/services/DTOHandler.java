package com.ticket.ticket.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ticket.ticket.DTOs.TicketDTO;
import com.ticket.ticket.DTOs.TourDTO;
import com.ticket.ticket.entities.Ticket;
import com.ticket.ticket.entities.Tour;

public class DTOHandler {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private TourService tourService;
    
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

    public TicketDTO convertTicketToDTO(Ticket ticket)
    {
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
    }

    public List<TourDTO> convertTourToDTO(List<Tour> tours)
    {
        List<TourDTO> tourDTOs = tours.stream().map(tour-> {
            TourDTO tourDTO = new TourDTO();
            tourDTO.setTourId(tour.getTourId());
            tourDTO.setTourName(tour.getTourName());
            tourDTO.setSignCar(tour.getSignCar());
            tourDTO.setDateStart(tour.getDateStart());
            tourDTO.setNumberCustomer(tour.getNumberCustomer());
            tourDTO.setSeatLeft(tour.getSeatLeft());

            return tourDTO;
        }).collect(Collectors.toList());
        return tourDTOs;
    }


    public TourDTO convertTourToDTO(Tour tour)
    {
        TourDTO tourDTO = new TourDTO();
        tourDTO.setTourId(tour.getTourId());
        tourDTO.setTourName(tour.getTourName());
        tourDTO.setSignCar(tour.getSignCar());
        tourDTO.setDateStart(tour.getDateStart());
        tourDTO.setNumberCustomer(tour.getNumberCustomer());
        tourDTO.setSeatLeft(tour.getSeatLeft());

        return tourDTO;
    }

}
