package com.carona.models;

import java.time.LocalTime;

public class NotificationConfigModel extends AbstractModel<Integer> {
    public NotificationConfigModel() {
    }

    public NotificationConfigModel(Integer id) {
        this.id = id;
    }

    public NotificationConfigModel(Integer id, UserModel userModel, Boolean receiveNotification, LocationModel placeOfDeparture, Double maxDistenceInKm,
    AvailableWeekdaysModel availableWeekdays, LocalTime initialDepartureTime, LocalTime finalDepartureTime) {
        this.id = id;
        this.userModel = userModel;
        this.receiveNotification = receiveNotification;
        this.maxDistanceInKm = maxDistenceInKm;
        this.placeOfDeparture = placeOfDeparture;
        this.availableWeekdays = availableWeekdays;
        this.initialDepartureTime = initialDepartureTime;
        this.finalDepartureTime = finalDepartureTime;
    }

    private Boolean receiveNotification;
    private LocationModel placeOfDeparture;
    private Double maxDistanceInKm;
    private AvailableWeekdaysModel availableWeekdays;
    private LocalTime initialDepartureTime;
    private LocalTime finalDepartureTime;
    private UserModel userModel;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public Double getMaxDistanceInKm() {
        return maxDistanceInKm;
    }

    public void setMaxDistanceInKm(Double maxDistanceInKm) {
        this.maxDistanceInKm = maxDistanceInKm;
    }

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
