package com.carona.filters;

import com.carona.models.AvailableWeekdaysModel;
import com.carona.models.LocationModel;

public class PostFilter {
    String descriptionOrTitle;
    AvailableWeekdaysModel availableWeekdaysModel;
    LocationModel departurePlace;

    public PostFilter(String descriptionOrTitle, AvailableWeekdaysModel availableWeekdaysModel, LocationModel departurePlace) {
        this.availableWeekdaysModel = availableWeekdaysModel;
        this.departurePlace = departurePlace;
        this.descriptionOrTitle = descriptionOrTitle;
    }


    public String getDescriptionOrTitle() {
        return descriptionOrTitle;
    }

    public void setDescriptionOrTitle(String descriptionOrTitle) {
        this.descriptionOrTitle = descriptionOrTitle;
    }


    public AvailableWeekdaysModel getAvailableWeekdaysModel() {
        return availableWeekdaysModel;
    }

    public void setAvailableWeekdaysModel(AvailableWeekdaysModel availableWeekdaysModel) {
        this.availableWeekdaysModel = availableWeekdaysModel;
    }

    
    public LocationModel getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(LocationModel departurePlace) {
        this.departurePlace = departurePlace;
    }

}
