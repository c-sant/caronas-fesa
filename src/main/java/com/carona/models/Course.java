package com.carona.models;

import java.util.HashMap;
import java.util.Map;

public enum Course {
    ComputerEngineering(0),
    FoodEngineering(1),
    BusinessAdministration(2),
    ControlAndAutomationEngineering(3);

    private Integer value;
    private static Map<Integer, Course> map = new HashMap<Integer, Course>();

    Course(Integer value) {
        this.value = value;
    }

    static {
        for (Course course : Course.values()) {
            map.put(course.value, course);
        }
    }

    public static Course fromInteger(Integer value) {
        return (Course) map.get(value);
    }

    public Integer getValue() {
        return value;
    }
}
