package com.carona.models;

import java.time.LocalTime;

public class NotificationConfigModel extends AbstractModel<Integer> {
    public NotificationConfigModel() {
    }

    public NotificationConfigModel(Integer id) {
        this.id = id;
    }

    public NotificationConfigModel(Integer id, Boolean receiveNotification, LocationModel placeOfDeparture,
    AvailableWeekdaysModel availableWeekdays, LocalTime initialDepartureTime, LocalTime finalDepartureTime) {
        this.id = id;
        this.receiveNotification = receiveNotification;
        this.placeOfDeparture = placeOfDeparture;
        this.availableWeekdays = availableWeekdays;
        this.initialDepartureTime = initialDepartureTime;
        this.finalDepartureTime = finalDepartureTime;
    }

    private Boolean receiveNotification;
    private LocationModel placeOfDeparture;
    private AvailableWeekdaysModel availableWeekdays;
    private LocalTime initialDepartureTime;
    private LocalTime finalDepartureTime;

    public Boolean getReceiveNotification() {
        return receiveNotification;
    }

    public void setReceiveNotification(Boolean receiveNotification) {
        this.receiveNotification = receiveNotification;
    }

    public LocationModel getPlaceOfDeparture() {
        return placeOfDeparture;
    }

    public void setPlaceOfDeparture(LocationModel placeOfDeparture) {
        this.placeOfDeparture = placeOfDeparture;
    }

    public AvailableWeekdaysModel getAvailableWeekdays() {
        return availableWeekdays;
    }

    public void setAvailableWeekdays(AvailableWeekdaysModel availableWeekdays) {
        this.availableWeekdays = availableWeekdays;
    }

    public LocalTime getInitialDepartureTime() {
        return initialDepartureTime;
    }

    public void setInitialDepartureTime(LocalTime initialDepartureTime) {
        this.initialDepartureTime = initialDepartureTime;
    }

    public LocalTime getFinalDepartureTime() {
        return finalDepartureTime;
    }

    public void setFinalDepartureTime(LocalTime finalDepartureTime) {
        this.finalDepartureTime = finalDepartureTime;
    }
}
