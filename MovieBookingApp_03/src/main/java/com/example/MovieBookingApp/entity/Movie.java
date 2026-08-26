package com.example.MovieBookingApp.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Movie {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String name;
     private String description;
     private int genre;
     private Integer duration;
     private LocalDate releaseDate;
     private String language;

     @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
     private List<Show> show;
}
