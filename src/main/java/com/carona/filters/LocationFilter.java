package com.carona.filters;

import com.carona.models.LocationModel;

public class LocationFilter {
    Double DISTANCE_BETWEEN_LAT_IN_KM = 111.0; // Valor aproximado, pois pode variar de 110.567 até 111.699, pelo formato eliptico que a Terra possui
    Double DISTANCE_BETWEEN_LON_IN_KM = 85.0;

    private LocationModel locationModel;
    private Integer distanceInKm;

    public LocationFilter(LocationModel locationModel, Integer distanceInKm) {
        this.locationModel = locationModel;
        this.distanceInKm = distanceInKm;
    }

    public String generateSqlFilter() {
        double latVar = distanceInKm / DISTANCE_BETWEEN_LAT_IN_KM;
        double lonVar = distanceInKm / DISTANCE_BETWEEN_LON_IN_KM;

        Double minLat = locationModel.getLatitude() - latVar;
        Double maxLat = locationModel.getLatitude() + latVar;

        Double minLon = locationModel.getLongitude() - lonVar;
        Double maxLon = locationModel.getLongitude() + lonVar;

        return "(l.latitude BETWEEN " + minLat + " AND " + maxLat +
            " AND l.longitude BETWEEN " + minLon + " AND " + maxLon + ")";
    }
}
