package com.carona.models;

import java.time.LocalTime;

public class NotificationModel extends AbstractModel<Integer> {

    public NotificationModel() {
    }

    public NotificationModel(Integer id) {
        this.id = id;
    }

    public NotificationModel(Integer id, UserModel subscriber, PostModel post, Boolean viewed, LocalTime notificationTime) {
        this.id = id;
        this.subscriber = subscriber;
        this.post = post;
        this.viewed = viewed;
        this.notificationTime = notificationTime;
    }

    private UserModel subscriber;
    private PostModel post;
    private Boolean viewed;
    private LocalTime notificationTime;

    public LocalTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }

    public UserModel getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(UserModel subscriber) {
        this.subscriber = subscriber;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel post) {
        this.post = post;
    }

}
