package com.ticket.ticket.caches;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.ticket.ticket.DTOs.TourDTO;
import com.ticket.ticket.entities.Tour;

@Configuration
public class AppConfig {
     @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Tour tour() {
        return new Tour(); // Cung cấp đối tượng Tour
    }

    @Bean
    public TourDTO tourDTO() {
        return new TourDTO(); // Cung cấp đối tượng Tour
    }
}
