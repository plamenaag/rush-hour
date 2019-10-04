package com.primeholding.rushhour.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Duration duration;
    private Double price;

    @OneToMany(mappedBy = "activity",
            cascade = CascadeType.REFRESH)
    private List<AppointmentActivity> appointmentActivities;

    public Activity() {

    }

    public Activity(Integer id, String name, Duration duration, Double price, List<AppointmentActivity> appointmentActivities) {
        this.setId(id);
        this.setName(name);
        this.setDuration(duration);
        this.setPrice(price);
        this.setAppointmentActivities(appointmentActivities);
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

    public List<AppointmentActivity> getAppointmentActivities() {
        if (appointmentActivities == null) {
            appointmentActivities = new ArrayList<>();
        }

        return appointmentActivities;
    }

    public void setAppointmentActivities(List<AppointmentActivity> appointmentActivities) {
        this.appointmentActivities = appointmentActivities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Activity activity = (Activity) o;
        return Objects.equals(name, activity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
