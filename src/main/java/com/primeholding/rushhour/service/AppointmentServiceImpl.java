package com.primeholding.rushhour.service;

import com.primeholding.rushhour.entity.Activity;
import com.primeholding.rushhour.entity.Appointment;
import com.primeholding.rushhour.entity.AppointmentActivity;
import com.primeholding.rushhour.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private AppointmentRepository appointmentRepository;
    private UserService userService;
    private ActivityService activityService;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserService userService,
                                  ActivityService activityService) {
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
        this.activityService = activityService;
    }

    @Override
    public List<Appointment> get() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> get(Integer appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    @Override
    public Optional<Appointment> add(Appointment appointment) {
        Optional<Appointment> preparedAppointment = prepareAppointment(appointment);

        if (!preparedAppointment.isPresent()) {
            return Optional.empty();
        }
        appointment = preparedAppointment.get();
        appointment.setEndDate(calculateEndDate(appointment));

        if (!isActivitiesOrderCorrect(appointment)) {
            return Optional.empty();
        }

        List<Integer> activityIdList = appointment.getAppointmentActivities().stream().map(p -> p.getActivity().getId()).collect(Collectors.toList());

        List<Appointment> overlappingAppointments = appointmentRepository.findOverlappingAppointments(appointment.getStartDate(),
                appointment.getEndDate(), appointment.getUser().getId(), activityIdList);

        if (!overlappingAppointments.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(appointmentRepository.save(appointment));
    }

    @Override
    public Optional<Appointment> update(Integer id, Appointment appointment) {
        Optional<Appointment> appointmentToUpdate = prepareAppointmentForUpdate(id, appointment);
        if (appointmentToUpdate.isPresent()) {
            if (!isActivitiesOrderCorrect(appointmentToUpdate.get())) {
                return Optional.empty();
            }

            appointmentToUpdate.get().setEndDate(calculateEndDate(appointmentToUpdate.get()));

            List<Integer> activityIdList = appointmentToUpdate.get().getAppointmentActivities().stream().map(p -> p.getActivity().getId()).collect(Collectors.toList());

            List<Appointment> overlappingAppointments = appointmentRepository.findOverlappingAppointments(appointmentToUpdate.get().getStartDate(),
                    appointmentToUpdate.get().getEndDate(), appointmentToUpdate.get().getUser().getId(), activityIdList)
                    .stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList());

            if (!overlappingAppointments.isEmpty()) {
                return Optional.empty();
            }

            return Optional.ofNullable(appointmentRepository.save(appointmentToUpdate.get()));
        }

        return Optional.empty();
    }

    private Optional<Appointment> prepareAppointmentForUpdate(Integer id, Appointment appointment) {
        Optional<Appointment> preparedAppointment = prepareAppointment(appointment);
        if (!preparedAppointment.isPresent()) {
            return Optional.empty();
        }
        appointment = preparedAppointment.get();

        Optional<Appointment> appointmentToUpdate = get(id);

        if (appointmentToUpdate.isPresent()) {
            List<AppointmentActivity> toRemove = new ArrayList<>();
            for (AppointmentActivity appointmentActivityToUpdate : appointmentToUpdate.get().getAppointmentActivities()) {
                Optional<AppointmentActivity> appointmentActivity = appointment.getAppointmentActivities().stream().filter(p -> p.getActivity().getId().equals(appointmentActivityToUpdate.getActivity().getId())).findFirst();
                if (appointmentActivity.isPresent()) {
                    appointmentActivityToUpdate.setOrderNum(appointmentActivity.get().getOrderNum());
                    appointment.getAppointmentActivities().remove(appointmentActivity.get());
                } else {
                    toRemove.add(appointmentActivityToUpdate);
                }
            }

            for (AppointmentActivity appointmentActivity : toRemove) {
                appointmentToUpdate.get().removeAppointmentActivity(appointmentActivity);
            }

            for (AppointmentActivity appointmentActivity : appointment.getAppointmentActivities()) {
                appointmentActivity.setAppointment(appointmentToUpdate.get());
                appointmentToUpdate.get().addAppointmentActivity(appointmentActivity);
            }

            appointmentToUpdate.get().setStartDate(appointment.getStartDate());

            return appointmentToUpdate;
        }

        return Optional.empty();
    }

    @Override
    public void delete(Integer appointmentId) {
        Optional<Appointment> appointment = get(appointmentId);
        if (appointment.isPresent()) {
            appointmentRepository.delete(appointment.get());
        }
    }

    private Instant calculateEndDate(Appointment appointment) {
        Duration totalDuration = Duration.ofSeconds(0);
        for (AppointmentActivity appointmentActivity : appointment.getAppointmentActivities()) {
            totalDuration = totalDuration.plus(appointmentActivity.getActivity().getDuration());
        }

        return appointment.getStartDate().plus(totalDuration);
    }

    private Optional<Appointment> prepareAppointment(Appointment appointment) {
        for (AppointmentActivity appointmentActivity : appointment.getAppointmentActivities()) {
            Optional<Activity> foundActivity = activityService.get(appointmentActivity.getActivity().getId());
            if (!foundActivity.isPresent()) {
                return Optional.empty();
            } else {
                appointmentActivity.setActivity(foundActivity.get());
                appointmentActivity.setAppointment(appointment);
            }
        }

        return Optional.ofNullable(appointment);
    }

    private Boolean isActivitiesOrderCorrect(Appointment appointment) {
        List<Integer> orderNums = appointment.getAppointmentActivities().stream().map(p -> p.getOrderNum()).collect(Collectors.toList());

        Collections.sort(orderNums);
        for (int i = 0; i < orderNums.size(); i++) {
            if (!orderNums.get(i).equals(i + 1)) {
                return false;
            }
        }

        return true;
    }
}
