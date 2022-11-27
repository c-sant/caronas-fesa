package com.carona.filters;

import com.carona.models.AvailableWeekdaysModel;

public class AvailableWeekdaysFilter {
    private AvailableWeekdaysModel availableWeekdaysModel;

    public AvailableWeekdaysFilter(AvailableWeekdaysModel availableWeekdaysModel) {
        this.availableWeekdaysModel = availableWeekdaysModel;
    }

    public String generateSqlFilter() {
        String strBuffer = "";
        if (availableWeekdaysModel.getSunday() == true) {
            strBuffer += " sunday = 1 OR";
        }
        if (availableWeekdaysModel.getMonday() == true) {
            strBuffer += " monday = 1 OR";
        }
        if (availableWeekdaysModel.getTuesday() == true) {
            strBuffer += " tuesday = 1 OR";
        }
        if (availableWeekdaysModel.getWednesday() == true) {
            strBuffer += " wednesday = 1 OR";
        }
        if (availableWeekdaysModel.getThursday() == true) {
            strBuffer += " thursday = 1 OR";
        }
        if (availableWeekdaysModel.getFriday() == true) {
            strBuffer += " friday = 1 OR";
        }
        if (availableWeekdaysModel.getSaturday() == true) {
            strBuffer += " saturday = 1 OR";
        }

        if (strBuffer.length() == 0) {
            strBuffer = "FALSE";
        } else {
            strBuffer = "(" + strBuffer.substring(0, strBuffer.length() - 3) + ")";
        }
        return strBuffer;
    }

    public AvailableWeekdaysModel getAvailableWeekdaysModel() {
        return availableWeekdaysModel;
    }

    public void setAvailableWeekdaysModel(AvailableWeekdaysModel availableWeekdaysModel) {
        this.availableWeekdaysModel = availableWeekdaysModel;
    }
}
