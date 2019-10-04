package com.primeholding.rushhour.service;

import com.primeholding.rushhour.entity.Activity;
import java.util.List;
import java.util.Optional;

public interface ActivityService {
    List<Activity> get();

    Optional<Activity> get(Integer activityId);

    Optional<Activity> add(Activity activity);

    Optional<Activity> update(Integer id, Activity activity);

    void delete(Integer activityId);
}
