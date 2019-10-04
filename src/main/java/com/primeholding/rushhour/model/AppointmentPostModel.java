package com.primeholding.rushhour.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AppointmentPostModel {
    private Instant startDate;
    private List<AppointmentActivityPostModel> activities;

    public AppointmentPostModel() {

    }

    public AppointmentPostModel(Instant startDate, List<AppointmentActivityPostModel> activities) {
        this.setStartDate(startDate);
        this.setActivities(activities);

    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public List<AppointmentActivityPostModel> getActivities() {
        if(activities == null){
            return activities = new ArrayList<>();
        }

        return activities;
    }

    public void setActivities(List<AppointmentActivityPostModel> activities) {
        this.activities = activities;
    }


}
