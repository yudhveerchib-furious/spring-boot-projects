package com.example.MovieBookingApp.Repository;

import com.example.MovieBookingApp.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheaterRepo extends JpaRepository<Theater,Long> {

   public Optional<List<Theater>> findByLocation(String location);
}
