package com.ticket.ticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.ticket.repositories.TourRepository;

@RestController
@RequestMapping(value = "/api/ticket-service")
public class TourController {
    @Autowired
    private TourRepository tourRepository;
}
