package com.example.HotelManagementSystem.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class HotelDto {
    private String name;
    private String address;
    private String city;
    private float rating;
    private int postalCode;
    @Column(name = "available")
    private boolean isAvailable;
}
