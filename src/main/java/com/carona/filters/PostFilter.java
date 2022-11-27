package com.carona.filters;


public class PostFilter {
    private String descriptionOrTitle;
    private AvailableWeekdaysFilter availableWeekdaysFilter;
    private LocationFilter departurePlace;

    public PostFilter(String descriptionOrTitle, AvailableWeekdaysFilter availableWeekdaysFilter, LocationFilter departurePlace) {
        this.availableWeekdaysFilter = availableWeekdaysFilter;
        this.departurePlace = departurePlace;
        this.descriptionOrTitle = descriptionOrTitle;
    }

    public String generateSqlFilter() {
        String query = "WHERE (title LIKE '%" +  descriptionOrTitle + "%'" +
            " OR description LIKE '%" + descriptionOrTitle + "%')";

        if (availableWeekdaysFilter != null) {
            query += " AND " + availableWeekdaysFilter.generateSqlFilter();
        }
        
        if (departurePlace != null) {
            query += " AND " + departurePlace.generateSqlFilter();
        }

        return query;
    }

    public String getDescriptionOrTitle() {
        return descriptionOrTitle;
    }

    public void setDescriptionOrTitle(String descriptionOrTitle) {
        this.descriptionOrTitle = descriptionOrTitle;
    }


    public AvailableWeekdaysFilter getAvailableWeekdaysFilter() {
        return availableWeekdaysFilter;
    }

    public void setAvailableWeekdaysFilter(AvailableWeekdaysFilter availableWeekdaysFilter) {
        this.availableWeekdaysFilter = availableWeekdaysFilter;
    }

    
    public LocationFilter getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(LocationFilter departurePlace) {
        this.departurePlace = departurePlace;
    }

}
