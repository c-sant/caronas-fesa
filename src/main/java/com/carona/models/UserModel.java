package com.carona.models;

public class UserModel extends AbstractModel<String> {

    public UserModel() {
    }

    public UserModel(String id) {
        this.id = id;
    }

    public UserModel(String id, String name, String description, Course course, String phoneNumber,
            String password) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.course = course;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    private String name;
    private String description;
    private Course course;
    private String phoneNumber;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}