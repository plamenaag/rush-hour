package com.primeholding.rushhour.model;

import java.time.Duration;

public class ActivityPostModel {
    private String name;
    private Duration duration;
    private Double price;

    public ActivityPostModel() {

    }

    public ActivityPostModel(String name, Duration duration, Double price) {
        this.setName(name);
        this.setDuration(duration);
        this.setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
