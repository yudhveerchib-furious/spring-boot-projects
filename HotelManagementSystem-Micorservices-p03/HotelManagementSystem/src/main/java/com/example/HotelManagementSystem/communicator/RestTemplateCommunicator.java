package com.example.HotelManagementSystem.communicator;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateCommunicator {

    private final RestTemplate restTemplate;

    public RestTemplateCommunicator(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Float getActualHotelRating(Long hotelid) {
        String url = "http://localhost:8084/rating/getratingbyhotelid/" + hotelid;        ResponseEntity<Float> hotelRating =
                restTemplate.getForEntity(url, Float.class);

        return hotelRating.getBody();
    }
}