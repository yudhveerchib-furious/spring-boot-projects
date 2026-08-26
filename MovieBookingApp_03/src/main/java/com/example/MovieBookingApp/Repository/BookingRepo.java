package com.example.MovieBookingApp.Repository;

import com.example.MovieBookingApp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
  public List<Booking> findByUserId(Long userid);

  public  List<Booking> findByShowId(Long id);


}