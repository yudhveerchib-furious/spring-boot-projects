package com.example.MovieBookingApp.service;

import com.example.MovieBookingApp.DTO.MovieDTO;
import com.example.MovieBookingApp.Repository.MovieRepository;
import com.example.MovieBookingApp.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
   @Autowired
   private MovieRepository movieRepository;

    public Movie addMovie(MovieDTO movieDTO) {
      Movie movie = new Movie();
      movie.setName(movieDTO.getName());
      movie.setDescription(movieDTO.getDescription());
      movie.setGenre(movieDTO.getGenre());
      movie.setDuration(movieDTO.getDuration());
      movie.setReleaseDate(movieDTO.getReleaseDate());
      movie.setLanguage(movieDTO.getLanguage());

      return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
      return movieRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre) {
      Optional<List<Movie>> box = movieRepository.findByGenre(genre);
      if(box.isPresent()) {
          return box.get();
      }
      else throw new RuntimeException("No movies found for genre " + genre);
    }

    public List<Movie> getMoviesByLanguage(String language) {
        Optional<List<Movie>> box = movieRepository.findByLanguage(language);
        if(box.isPresent()) {
            return box.get();
        }
        else throw new RuntimeException("No movies found for language " + language);
    }


    public List<Movie> getMoviesByTitle(String title) {
        Optional<List<Movie>> box = movieRepository.findByName(title);
        if(box.isPresent()) {
            return box.get();
        }
        else throw new RuntimeException("No movies found for title " + title);
    }

    public Movie updateMovie(Long id, MovieDTO movieDTO) {

       Movie movie = movieRepository.findById(id).orElseThrow(() ->
               new RuntimeException("no movie time"));

       movie.setName(movieDTO.getName());
       movie.setDescription(movieDTO.getDescription());
       movie.setGenre(movieDTO.getGenre());
       movie.setDuration(movieDTO.getDuration());
       movie.setReleaseDate(movieDTO.getReleaseDate());
       movie.setLanguage(movieDTO.getLanguage());

       return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

}