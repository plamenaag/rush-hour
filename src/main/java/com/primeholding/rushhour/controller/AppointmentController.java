package com.primeholding.rushhour.controller;

import com.primeholding.rushhour.entity.Appointment;
import com.primeholding.rushhour.entity.User;
import com.primeholding.rushhour.model.AppointmentGetModel;
import com.primeholding.rushhour.model.AppointmentPostModel;
import com.primeholding.rushhour.security.UserPrincipal;
import com.primeholding.rushhour.security.exception.AppException;
import com.primeholding.rushhour.service.ActivityService;
import com.primeholding.rushhour.service.AppointmentService;
import com.primeholding.rushhour.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private AppointmentService appointmentService;
    private UserService userService;
    private ActivityService activityService;
    private ModelMapper modelMapper;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, ModelMapper modelMapper, UserService userService,
                                 ActivityService activityService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
        this.activityService = activityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public HttpEntity get(@PathVariable("id") Integer id) {
        Optional<Appointment> appointment = appointmentService.get(id);
        if (appointment.isPresent()) {
            return ResponseEntity.ok(modelMapper.map(appointment.get(), AppointmentGetModel.class));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public HttpEntity get() {
        List<Appointment> appointments = appointmentService.get();
        List<AppointmentGetModel> appointmentGetModels = new ArrayList<>();
        for (Appointment appointment : appointments) {
            appointmentGetModels.add(modelMapper.map(appointment, AppointmentGetModel.class));
        }

        return ResponseEntity.ok(appointmentGetModels);
    }

    @PostMapping
    public HttpEntity add(@RequestBody AppointmentPostModel appointmentPostModel) {
        Appointment appointment = modelMapper.map(appointmentPostModel, Appointment.class);

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.get(userPrincipal.getId()).orElseThrow(() -> new AppException("User not found."));
        user.setId(userPrincipal.getId());
        appointment.setUser(user);

        Optional<Appointment> savedAppointment = appointmentService.add(appointment);
        if (savedAppointment.isPresent()) {
            return ResponseEntity.ok(modelMapper.map(savedAppointment.get(), AppointmentGetModel.class));
        }

        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    @PutMapping("/{id}")
    public HttpEntity update(@PathVariable(required = true) Integer id, @RequestBody AppointmentPostModel appointmentPostModel) {
        Appointment appointment = modelMapper.map(appointmentPostModel, Appointment.class);

        Optional<Appointment> updatedAppointment = appointmentService.update(id, appointment);
        if (updatedAppointment.isPresent()) {
            return ResponseEntity.ok(modelMapper.map(updatedAppointment.get(), AppointmentGetModel.class));
        }

        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public HttpEntity delete(@PathVariable(required = true) Integer id) {
        if (appointmentService.get(id).isPresent()) {
            appointmentService.delete(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
