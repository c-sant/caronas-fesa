package com.carona.models;

import java.util.HashMap;
import java.util.Map;

public enum CourseModel {
    ComputerEngineering(0),
    FoodEngineering(1),
    BusinessAdministration(2),
    ControlAndAutomationEngineering(3);

    private Integer value;
    private static Map<Integer, CourseModel> map = new HashMap<Integer, CourseModel>();

    CourseModel(Integer value) {
        this.value = value;
    }

    static {
        for (CourseModel course : CourseModel.values()) {
            map.put(course.value, course);
        }
    }

    public static CourseModel fromInteger(Integer value) {
        return (CourseModel) map.get(value);
    }

    public Integer getValue() {
        return value;
    }
}
