package com.primeholding.rushhour.model;

public class ActivityGetModel {
    private Integer id;
    private String name;
    private String duration;
    private Double price;

    public ActivityGetModel() {

    }

    public ActivityGetModel(Integer id, String name, String duration, Double price) {
        this.setId(id);
        this.setName(name);
        this.setDuration(duration);
        this.setPrice(price);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
