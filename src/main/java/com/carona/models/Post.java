package com.carona.models;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Post extends AbstractModel<Integer> {

    public Post() {
    }

    public Post(String title, String description, Location placeOfDeparture, Location destination,
            Integer availableSeats, LocalDateTime departureTime) {
        this.title = title;
        this.description = description;
        this.placeOfDeparture = placeOfDeparture;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.departureTime = departureTime;
    }

    private String title;
    private String description;
    private Location placeOfDeparture;
    private Location destination;
    private final List<Boolean> availableWeekdays = Arrays.asList(false, false, false, false, false, false, false);
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

    public Location getPlaceOfDeparture() {
        return placeOfDeparture;
    }

    public void setPlaceOfDeparture(Location placeOfDeparture) {
        this.placeOfDeparture = placeOfDeparture;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public List<Boolean> getAvailableWeekdays() {
        return availableWeekdays;
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
