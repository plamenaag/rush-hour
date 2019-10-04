package com.primeholding.rushhour.repository;

import com.primeholding.rushhour.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    Optional<Activity> findByName(String name);
}
