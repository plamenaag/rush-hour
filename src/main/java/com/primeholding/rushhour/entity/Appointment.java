package com.primeholding.rushhour.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Instant startDate;
    private Instant endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "appointment",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentActivity> appointmentActivities;

    public Appointment() {

    }

    public Appointment(Integer id, Instant startDate, Instant endDate, User user, List<AppointmentActivity> appointmentActivities) {
        this.setId(id);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setUser(user);
        this.setAppointmentActivities(appointmentActivities);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void addAppointmentActivity(AppointmentActivity appointmentActivity) {
        appointmentActivities.add(appointmentActivity);
    }

    public void removeAppointmentActivity(AppointmentActivity appointmentActivity) {
        this.getAppointmentActivities().remove(appointmentActivity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Appointment appointment = (Appointment) o;
        return Objects.equals(id, appointment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}









