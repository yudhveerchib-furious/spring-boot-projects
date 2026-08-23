package com.example.HotelManagementSystem.service;

import com.example.HotelManagementSystem.Repo.HotelRepository;
import com.example.HotelManagementSystem.communicator.RestTemplateCommunicator;
import com.example.HotelManagementSystem.dto.HotelDto;
import com.example.HotelManagementSystem.dto.UpdateHostelAddressDto;
import com.example.HotelManagementSystem.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private RestTemplateCommunicator restTemplateCommunicator;

    @Autowired
    private HotelRepository hotelRepository;

    public Hotel saveHotel(HotelDto hotelDTO) {
        Hotel hotel = new Hotel();

        hotel.setName(hotelDTO.getName());
        hotel.setAddress(hotelDTO.getAddress());
        hotel.setCity(hotelDTO.getCity());
        hotel.setPostalCode(hotelDTO.getPostalCode());
        hotel.setRating(hotelDTO.getRating());
        hotel.setAvailable(hotelDTO.isAvailable());

        hotelRepository.save(hotel);

        return hotel;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id) {

        Float hotelActualRating =
                restTemplateCommunicator.getActualHotelRating(id);

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotel.setRating(hotelActualRating);

        return hotel;
    }

    public Hotel updateHotelById(Long id, HotelDto hotelDto) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotel.setName(hotelDto.getName());
        hotel.setAddress(hotelDto.getAddress());
        hotel.setCity(hotelDto.getCity());
        hotel.setPostalCode(hotelDto.getPostalCode());
        hotel.setRating(hotelDto.getRating());
        hotel.setAvailable(hotelDto.isAvailable());

        return hotelRepository.save(hotel);
    }

    public Hotel deleteById(Long id) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotelRepository.deleteById(id);

        return hotel;
    }

    public Hotel updateHostelAddress(UpdateHostelAddressDto updateHostelAddressDto, Long id) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotel.setAddress(updateHostelAddressDto.getAddress());

        return hotelRepository.save(hotel);
    }
}