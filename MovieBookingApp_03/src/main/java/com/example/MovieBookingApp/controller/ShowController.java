package com.example.MovieBookingApp.controller;

import com.example.MovieBookingApp.DTO.ShowDTO;
import com.example.MovieBookingApp.entity.Show;
import com.example.MovieBookingApp.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show")
public class ShowController {

     @Autowired
     private ShowService showService;

     @PostMapping("/createshow")
     public ResponseEntity<Show> createShow(@RequestBody ShowDTO showDTO) {
         return ResponseEntity.ok(showService.createShow(showDTO));
     }

     @GetMapping("/getallshows")
     public ResponseEntity<List<Show>> getAllShow() {
         return ResponseEntity.ok(showService.getAllShows());
     }

     @GetMapping("/getshowbymovie")
     public ResponseEntity<List<Show>> getShowByMovie(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowByMovie(id));
     }

    @GetMapping("/getshowbytheater")
    public ResponseEntity<List<Show>> getShowByTheater(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowByTheater(id));
    }

    @PutMapping("/updateshow,{id}")
    public ResponseEntity<Show> updateShow(@PathVariable Long id, @RequestBody ShowDTO showDTO) {
         return ResponseEntity.ok(showService.updateShow(id,showDTO));
    }

    @DeleteMapping("/deleteshow/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id, @RequestBody ShowDTO showDTO) {
       showService.deleteShow(id);
       return ResponseEntity.ok().build();
    }

}
