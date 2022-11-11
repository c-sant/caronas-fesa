package com.carona.models;

public class Notification {

    public Notification() {
    }

    public Notification(User target, Post post) {
        this.target = target;
        this.post = post;
    }

    private User target;
    private Post post;

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
