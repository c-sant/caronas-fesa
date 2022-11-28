package com.carona.models;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class PostModel extends AbstractModel<Integer> {

    public PostModel() {
    }

    public PostModel(Integer id) {
        this.id = id;
    }
    
    public PostModel(Integer id, UserModel creator, String title, String description, LocationModel placeOfDeparture,
            LocationModel destination, AvailableWeekdaysModel availableWeekdays, Integer availableSeats,
            LocalTime departureTime, LocalDateTime createdTime) {
        this.id = id;
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.placeOfDeparture = placeOfDeparture;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.availableWeekdays = availableWeekdays;
        this.departureTime = departureTime;
        this.createdTime = createdTime;
    }

    private UserModel creator;
    private String title;
    private String description;
    private LocationModel placeOfDeparture;
    private LocationModel destination;
    private AvailableWeekdaysModel availableWeekdays;
    private Integer availableSeats;
    private LocalTime departureTime;
    private LocalDateTime createdTime;

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationModel getPlaceOfDeparture() {
        return placeOfDeparture;
    }

    public void setPlaceOfDeparture(LocationModel placeOfDeparture) {
        this.placeOfDeparture = placeOfDeparture;
    }

    public LocationModel getDestination() {
        return destination;
    }

    public void setDestination(LocationModel destination) {
        this.destination = destination;
    }

    public AvailableWeekdaysModel getAvailableWeekdays() {
        return availableWeekdays;
    }

    public void setAvailableWeekdays(AvailableWeekdaysModel availableWeekdays) {
        this.availableWeekdays = availableWeekdays;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

}
