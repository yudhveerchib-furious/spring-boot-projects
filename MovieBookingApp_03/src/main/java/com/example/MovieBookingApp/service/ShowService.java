package com.example.MovieBookingApp.service;

import com.example.MovieBookingApp.DTO.ShowDTO;
import com.example.MovieBookingApp.Repository.MovieRepository;
import com.example.MovieBookingApp.Repository.ShowRepo;
import com.example.MovieBookingApp.Repository.TheaterRepo;
import com.example.MovieBookingApp.entity.Booking;
import com.example.MovieBookingApp.entity.Movie;
import com.example.MovieBookingApp.entity.Show;
import com.example.MovieBookingApp.entity.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepo showRepo;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepo theaterRepo;

    public Show createShow(ShowDTO showDTO) {
        Movie movie = movieRepository.findById(showDTO.getMovieId()).orElseThrow(
                () -> new RuntimeException("Movie with id " + showDTO.getMovieId() + " not found")
        );

        Theater theater = theaterRepo.findById(showDTO.getTheaterId()).orElseThrow(
                () -> new RuntimeException("Theater with id " + showDTO.getTheaterId() + " not found")
        );


        Show show = new Show();
        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheater(theater);

       return showRepo.save(show);
    }


    public List<Show> getAllShows() {
      return showRepo.findAll();
    }

    public List<Show> getShowByMovie(Long id) {
        Optional<List<Show>> box = showRepo.findByMovieId(id);

        if(box.isPresent()) {
            return box.get();
        }
        else {
            throw new RuntimeException("Movie with id " + id + " not found");
        }

    }

    public List<Show> getShowByTheater(Long id) {
        Optional<List<Show>> box = showRepo.findByTheaterId(id);

        if(box.isPresent()) {
            return box.get();
        }
        else {
            throw new RuntimeException("Theater with id " + id + " not found");
        }
    }

    public Show updateShow(Long id, ShowDTO showDTO) {
        Show show = showRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Show with id " + id + " not found")
        );

        Movie movie = movieRepository.findById(showDTO.getMovieId()).orElseThrow(
                () -> new RuntimeException("Movie with id " + showDTO.getMovieId() + " not found")
        );

        Theater theater = theaterRepo.findById(showDTO.getTheaterId()).orElseThrow(
                () -> new RuntimeException("Theater with id " + showDTO.getTheaterId() + " not found")
        );

        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheater(theater);
       
        return showRepo.save(show);
    }
    // VERY IMPORTANT
    public void deleteShow(Long id) {
      if(!showRepo.existsById(id)) {
          throw new RuntimeException("Show with id " + id + " not found");
      }
      List<Booking> bookings = showRepo.findById(id).get()
              .getBookings();

      if(!bookings.isEmpty()) {
          throw new RuntimeException("Booking with id " + id + " not found");
      }
      showRepo.deleteById(id);
    }
}
