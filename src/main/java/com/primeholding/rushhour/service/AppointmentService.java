package com.primeholding.rushhour.service;

import com.primeholding.rushhour.entity.Appointment;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> get();

    Optional<Appointment> get(Integer appointmentId);

    Optional<Appointment> add(Appointment appointment);

    Optional<Appointment> update(Integer id, Appointment appointment);

    void delete(Integer appointmentId);
}
