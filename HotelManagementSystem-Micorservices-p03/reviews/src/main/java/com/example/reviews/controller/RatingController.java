package com.example.reviews.controller;

import com.example.reviews.entity.Rating;
import com.example.reviews.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @PostMapping("/addrating")
    public ResponseEntity<Rating> addRating(@RequestBody Rating rating) {
       Rating ratingNew =  ratingService.addRating(rating);
       return new ResponseEntity(ratingNew, HttpStatus.CREATED);

    }

    @GetMapping("/getratingbyhotelid/{hotelid}")
    public ResponseEntity<Float> getRatingByHotelId(@PathVariable Long hotelid) {
           Rating rating =  ratingService.getRatingByHotelId(hotelid);
           return new ResponseEntity(rating.getHotelActualRating(), HttpStatus.OK);
    }


}
