package com.ticket.ticket.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.ticket.DTOs.TicketDTO;
import com.ticket.ticket.DTOs.TourDTO;
import com.ticket.ticket.entities.PagedResponse;
import com.ticket.ticket.entities.Tour;
import com.ticket.ticket.repositories.TourRepository;
import com.ticket.ticket.services.DTOHandler;
import com.ticket.ticket.services.TourService;

@RestController
@RequestMapping(value = "/api/ticket-service")
public class TourController {
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourService tourService;

    @GetMapping(value = "/tour")
    public ResponseEntity<PagedResponse<TourDTO>> getAllTour()
    {
         try {
            PagedResponse<TourDTO> tours = tourService.tourFilterHeandler(
                "", 
                "", 
                LocalDateTime.parse("1800-01-13T03:09:42.411"),
                LocalDateTime.parse("2400-01-13T03:09:42.411"), 
                "", 
                0, 
                12);
            
            return new ResponseEntity<>(tours, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value ="/tour/search")
    public ResponseEntity<PagedResponse<TourDTO>> getTourByFilter(
        @RequestParam(name = "tourId", required = false) String tourId,
        @RequestParam(name = "tourName", required = false) String tourName,
        @RequestParam(name = "dateStartBegin", defaultValue = "1800-01-13T03:09:42.411") @DateTimeFormat LocalDateTime dateStartBegin,
        @RequestParam(name = "dateStartEnd", defaultValue = "2400-01-13T03:09:42.411") @DateTimeFormat LocalDateTime dateStartEnd,
        @RequestParam(name = "signCar", required = false) String signCar,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "12") int size

    )
    {
        try {
            PagedResponse<TourDTO> tours = tourService.tourFilterHeandler(
                tourId, 
                tourName, 
                dateStartBegin, 
                dateStartEnd, 
                signCar, 
                page, 
                size);
            return new ResponseEntity<>(tours, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/tour/getByTourId")
    public ResponseEntity<TourDTO> getTourById(
        @RequestParam(name = "tourId") String tourId
    )
    {
        try {
            Optional<Tour> tour = tourRepository.findById(tourId);
            return new ResponseEntity<>(new DTOHandler().convertTourToDTO(tour.get()),HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/tour")
    public ResponseEntity<TourDTO> createTour(
        @RequestBody Tour tour
    )
    {
        try {
            Tour _tour = tourRepository.save(tour);
            return new ResponseEntity<>(new DTOHandler().convertTourToDTO(_tour), HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/tour")
    public ResponseEntity<TourDTO> updateTour(
        @RequestBody Tour tour
    )
    {
        Optional<Tour> tourData = tourRepository.findById(tour.getTourId().toString());
        if (tourData.isPresent())
        {
            Tour _tour =tourData.get();
            _tour.setTourId(tour.getTourId());
            _tour.setTourName(tour.getTourName());
            _tour.setDateStart(tour.getDateStart());
            _tour.setSignCar(tour.getSignCar());
            _tour.setNumberCustomer(tour.getNumberCustomer());
            _tour.setSeatLeft(tour.getSeatLeft());

            Tour returnedTour = tourRepository.save(_tour);
            return new ResponseEntity<>(new DTOHandler().convertTourToDTO(returnedTour), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/tour")
    public ResponseEntity<HttpStatus> deleteTourById(
        @RequestParam(name = "tourId") String tourId
    )
    {
        try {
            tourRepository.deleteById(tourId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
