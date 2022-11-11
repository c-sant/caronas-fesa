package com.carona.models;

public class NotificationModel {

    public NotificationModel() {
    }

    public NotificationModel(UserModel target, PostModel post) {
        this.target = target;
        this.post = post;
    }

    private UserModel target;
    private PostModel post;

    public UserModel getTarget() {
        return target;
    }

    public void setTarget(UserModel target) {
        this.target = target;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel post) {
        this.post = post;
    }

}
