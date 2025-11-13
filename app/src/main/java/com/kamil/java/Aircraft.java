package com.kamil.java;

public class Aircraft {
    public long id;
    public String name;
    public String model;
    public int yearOfCreation;
    public int yearOfInspection;

    public Aircraft(long id, String name, String model, int yearOfCreation, int yearOfInspection) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.yearOfCreation = yearOfCreation;
        this.yearOfInspection = yearOfInspection;
    }
}
