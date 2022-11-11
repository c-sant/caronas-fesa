package com.carona.models;

import java.time.LocalDateTime;

public class PostModel extends AbstractModel<Integer> {

    public PostModel() {
    }

    public PostModel(String title, String description, LocationModel placeOfDeparture, LocationModel destination,
            AvailableWeekdaysModel availableWeekdays, Integer availableSeats, LocalDateTime departureTime) {
        this.title = title;
        this.description = description;
        this.placeOfDeparture = placeOfDeparture;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.departureTime = departureTime;
    }

    private String title;
    private String description;
    private LocationModel placeOfDeparture;
    private LocationModel destination;
    private AvailableWeekdaysModel availableWeekdays;
    private Integer availableSeats;
    private LocalDateTime departureTime;

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

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

}
