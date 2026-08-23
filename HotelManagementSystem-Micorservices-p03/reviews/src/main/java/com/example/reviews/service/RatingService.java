package com.example.reviews.service;

import com.example.reviews.entity.Rating;
import com.example.reviews.repository.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepo ratingRepo;

    public Rating addRating(Rating rating) {
       return ratingRepo.save(rating);
    }

    public Rating getRatingByHotelId(Long hotelid) {
         return ratingRepo.findByHotelId(hotelid);
    }
}
