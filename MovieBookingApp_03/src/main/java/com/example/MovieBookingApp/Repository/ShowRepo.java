package com.example.MovieBookingApp.Repository;

import com.example.MovieBookingApp.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepo extends JpaRepository<Show, Long> {

    public Optional<List<Show>> findByMovieId(Long id);


    public Optional<List<Show>> findByTheaterId(Long id);
}
