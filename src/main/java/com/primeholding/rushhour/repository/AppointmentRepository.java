package com.primeholding.rushhour.repository;

import com.primeholding.rushhour.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "Select DISTINCT * from appointment as app " +
            " join appointment_activity as act_app on act_app.appointment_id = app.id " +
            " where app.start_date < :endDate and app.end_date > :startDate and " +
            " (act_app.activity_id in :activityIdList or app.user_id = :userId)", nativeQuery = true)
    List<Appointment> findOverlappingAppointments(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate,
                                                  @Param("userId") Integer userId, @Param("activityIdList") List<Integer> activityIdList);
}
