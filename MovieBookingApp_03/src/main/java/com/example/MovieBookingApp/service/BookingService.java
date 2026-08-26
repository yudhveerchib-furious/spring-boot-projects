package com.example.MovieBookingApp.service;

import com.example.MovieBookingApp.DTO.BookingDTO;
import com.example.MovieBookingApp.Repository.BookingRepo;
import com.example.MovieBookingApp.Repository.ShowRepo;
import com.example.MovieBookingApp.Repository.UserRepo;
import com.example.MovieBookingApp.entity.Booking;
import com.example.MovieBookingApp.entity.BookingStatus;
import com.example.MovieBookingApp.entity.Show;
import com.example.MovieBookingApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ShowRepo showRepo;

    @Autowired
    private UserRepo  userRepo;

    public Booking createBooking(BookingDTO bookingDTO) {
        Show show = showRepo.findById(bookingDTO.getShowId()).orElseThrow(
                () -> new RuntimeException("No show found!")
        );

        if(!isSeatsAvailable(show.getId(),bookingDTO.getNumberOfSeats())){
            throw new RuntimeException("Seats not available");
        }
        if(bookingDTO.getSeatNumbers().size() != bookingDTO.getNumberOfSeats()){
            throw new RuntimeException("Number of seats not available");
        }
        validateDuplicateSeat(show.getId(), bookingDTO.getSeatNumbers());

        User user = userRepo.findById(bookingDTO.getUserId()).orElseThrow(
                () -> new RuntimeException("No user found!")
        );

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
        booking.setSeatNumbers(bookingDTO.getSeatNumbers());
        booking.setPrice(calcTotalAmount(show.getPrice(), bookingDTO.getNumberOfSeats()));
        booking.setBookingTime(bookingDTO.getBookingTime());
        booking.setBookingStatus(bookingDTO.getBookingStatus());

        return bookingRepo.save(booking);
    }

    public boolean isSeatsAvailable(Long showId, Integer numberOfSeats){
        Show show = showRepo.findById(showId).orElseThrow(
                () -> new RuntimeException("No show found!")
        );

        int bookedSeats = show.getBookings()
                .stream()
                .filter(booking ->
                        booking.getBookingStatus() != BookingStatus.CANCELLED)
                .mapToInt(Booking::getNumberOfSeats)
                .sum();

        return (show.getTheater().getTheaterCapacity() - bookedSeats) >= numberOfSeats;
    }

    public void validateDuplicateSeat(Long showId, List<String> seatNumbers){
        Show show = showRepo.findById(showId).orElseThrow(
                () -> new RuntimeException("No show found!")
        );
        Set<String> occupiedSeats = show.getBookings().stream()
                .filter(b -> b.getBookingStatus() != BookingStatus.CANCELLED)
                .flatMap(b -> b.getSeatNumbers().stream())
                .collect(Collectors.toSet());

        List<String> duplicateSeats = seatNumbers.stream()
                .filter(occupiedSeats::contains)
                .collect(Collectors.toList());

        if(!duplicateSeats.isEmpty()) {
            throw new RuntimeException("Duplicate seats found");
        }
    }

    public Double calcTotalAmount(Double price, Integer numberOfSeats){
        return price * numberOfSeats;
    }

    public List<Booking> getUserBookings(Long userid) {
       return bookingRepo.findByUserId(userid);
    }

    public List<Booking> getShowBookings(Long id) {
        return bookingRepo.findByShowId(id);
    }

    public Booking confirmBooking(Long id) {
         Booking booking = bookingRepo.findById(id).orElseThrow(
                 () -> new RuntimeException("No Booking found!")
         );

         if(booking.getBookingStatus() != BookingStatus.PENDING){
             throw new RuntimeException("Booking status not available");
         }

         booking.setBookingStatus(BookingStatus.CONFIRMED);
         return bookingRepo.save(booking);
    }

    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepo.findById(id).orElseThrow(
                () -> new RuntimeException("No Booking found!")
        );

        validateCancellation(booking);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        return bookingRepo.save(booking);
    }

    public void validateCancellation(Booking booking) {
        LocalDateTime showTime = booking.getShow().getShowTime();
        LocalDateTime deadlineTime = showTime.minusHours(2);

        if(LocalDateTime.now().isAfter(deadlineTime)) {
            throw new RuntimeException("Cancellation time not available");
        }

        if(booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking cancelled Already");
        }
    }

}
