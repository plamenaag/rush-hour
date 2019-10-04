package com.primeholding.rushhour.configuration;

import com.primeholding.rushhour.entity.Activity;
import com.primeholding.rushhour.entity.Appointment;
import com.primeholding.rushhour.entity.AppointmentActivity;
import com.primeholding.rushhour.model.AppointmentActivityPostModel;
import com.primeholding.rushhour.model.AppointmentPostModel;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import java.util.ArrayList;
import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(AppointmentPostModel.class, Appointment.class)
                .addMappings(mapper -> mapper.using(ctx -> {
                    List<AppointmentActivityPostModel> source = (List<AppointmentActivityPostModel>) ctx.getSource();
                    List<AppointmentActivity> appointmentActivities = new ArrayList<>();
                    for (AppointmentActivityPostModel appointmentActivityPostModel : source) {
                        AppointmentActivity appointmentActivity = new AppointmentActivity();
                        Activity activity = new Activity();
                        activity.setId(appointmentActivityPostModel.getActivityId());
                        appointmentActivity.setActivity(activity);
                        appointmentActivity.setOrderNum(appointmentActivityPostModel.getOrderNum());
                        appointmentActivities.add(appointmentActivity);
                    }

                    return appointmentActivities;
                }).map(AppointmentPostModel::getActivities, Appointment::setAppointmentActivities));

        return modelMapper;
    }
}
