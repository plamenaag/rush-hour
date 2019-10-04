package com.primeholding.rushhour.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AppointmentGetModel {
    private Integer id;
    private Instant startDate;
    private Instant endDate;
    private UserGetModel user;
    private List<AppointmentActivityGetModel> activities;

    public AppointmentGetModel() {

    }

    public AppointmentGetModel(Integer id, Instant startDate, Instant endDate, UserGetModel user, List<AppointmentActivityGetModel> activities) {
        this.setId(id);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setUser(user);
        this.setActivities(activities);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public UserGetModel getUser() {
        return user;
    }

    public void setUser(UserGetModel user) {
        this.user = user;
    }

    public List<AppointmentActivityGetModel> getActivities() {
        if (activities == null) {
            activities = new ArrayList<>();
        }
        return activities;
    }

    public void setActivities(List<AppointmentActivityGetModel> activities) {
        this.activities = activities;
    }
}
