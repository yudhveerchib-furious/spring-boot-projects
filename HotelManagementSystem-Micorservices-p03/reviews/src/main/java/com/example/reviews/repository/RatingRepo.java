package com.example.reviews.repository;

import com.example.reviews.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {

    public Rating findByHotelId(Long hotelid);

}
