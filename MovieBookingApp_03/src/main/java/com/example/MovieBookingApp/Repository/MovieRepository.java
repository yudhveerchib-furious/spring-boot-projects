package com.example.MovieBookingApp.Repository;

import com.example.MovieBookingApp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

   public Optional<List<Movie>> findByGenre(String genre);

   public Optional<List<Movie>> findByLanguage(String language);

   public Optional<List<Movie>> findByName(String title);
}
