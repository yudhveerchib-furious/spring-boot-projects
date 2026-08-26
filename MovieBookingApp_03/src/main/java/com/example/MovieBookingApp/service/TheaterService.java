package com.example.MovieBookingApp.service;

import com.example.MovieBookingApp.DTO.TheaterDTO;
import com.example.MovieBookingApp.Repository.TheaterRepo;
import com.example.MovieBookingApp.entity.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {
    @Autowired
    private TheaterRepo theaterRepo;


    public Theater addTheater(TheaterDTO theaterDTO) {
       Theater theater = new Theater();
       theater.setTheaterName(theaterDTO.getTheaterName());
       theater.setTheaterCapacity(theaterDTO.getTheaterCapacity());
       theater.setTheaterLocation(theaterDTO.getTheaterLocation());
       theater.setTheaterScreenType(theaterDTO.getTheaterScreenType());

       return theaterRepo.save(theater);
    }


    public List<Theater> getTheaterByLocation(String location) {
       Optional<List<Theater>> box =  theaterRepo.findByLocation(location);
       if(box.isPresent()){
           return box.get();
       }
       else throw new RuntimeException("No such location" +  location);
    }

    public Theater updateTheater(Long id, TheaterDTO theaterDTO) {
       Theater theater = theaterRepo.findById(id).orElseThrow(()
       -> new RuntimeException("No such theater with id " + id));

       theater.setTheaterName(theaterDTO.getTheaterName());
       theater.setTheaterCapacity(theaterDTO.getTheaterCapacity());
       theater.setTheaterLocation(theaterDTO.getTheaterLocation());
       theater.setTheaterScreenType(theaterDTO.getTheaterScreenType());

       return theaterRepo.save(theater);
    }

   public void deleteTheater(Long id) {
        theaterRepo.deleteById(id);
   }


}
