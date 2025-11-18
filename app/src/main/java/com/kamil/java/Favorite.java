package com.kamil.java;
public class Favorite {
    public User user;
    public Aircraft aircraft;
    public String creationDate;
    public Favorite(User user, Aircraft aircraft, String creationDate) {
        this.user = user;
        this.aircraft = aircraft;
        this.creationDate = creationDate;
    }
}
