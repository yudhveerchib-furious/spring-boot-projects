package com.example.HotelManagementSystem.controller;

import com.example.HotelManagementSystem.dto.HotelDto;
import com.example.HotelManagementSystem.dto.UpdateHostelAddressDto;
import com.example.HotelManagementSystem.entity.Hotel;
import com.example.HotelManagementSystem.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

     @Autowired
     private HotelService hotelService;

     @PostMapping
     public Hotel createHotel(@RequestBody HotelDto hotelDto) {
          return hotelService.saveHotel(hotelDto);
     }

     @GetMapping("/getallhotels")
     public List<Hotel> getAllHotels() {
          return hotelService.getAllHotels();
     }

     @GetMapping("/getallhotels/{id}")
     public Hotel getHotelById(@PathVariable Long id) {
          return hotelService.getHotelById(id);
     }

     @PutMapping("/updateHotel/{id}")
     public Hotel updateHotel(
             @RequestBody HotelDto hotelDto,
             @PathVariable Long id) {

          return hotelService.updateHotelById(id, hotelDto);
     }

     @DeleteMapping("/deleteHotel/{id}")
     public Hotel deleteHotel(@PathVariable Long id) {
          return hotelService.deleteById(id);
     }

     @PutMapping("/updateHotelAddress/{id}")
     public Hotel updateHotelAddress(
             @RequestBody UpdateHostelAddressDto updateHostelAddressDto,
             @PathVariable Long id) {

          return hotelService.updateHostelAddress(
                  updateHostelAddressDto,
                  id
          );
     }
}