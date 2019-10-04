package com.primeholding.rushhour.service;

import com.primeholding.rushhour.entity.Activity;
import com.primeholding.rushhour.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {
    private ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> get() {
        return activityRepository.findAll();
    }

    @Override
    public Optional<Activity> get(Integer activityId) {
        return activityRepository.findById(activityId);
    }

    @Override
    public Optional<Activity> add(Activity activity) {
        if (activityRepository.findByName(activity.getName()).isPresent()) {
            return Optional.empty();
        }

        return Optional.ofNullable(activityRepository.save(activity));
    }

    @Override
    public Optional<Activity> update(Integer id, Activity activity) {
        Optional<Activity> activityToUpdate = get(id);

        if (activityToUpdate.isPresent()) {
            activityToUpdate.get().setName(activity.getName());
            activityToUpdate.get().setPrice(activity.getPrice());
            activityToUpdate.get().setDuration(activity.getDuration());

            return Optional.ofNullable(activityRepository.save(activityToUpdate.get()));
        }

        return Optional.empty();
    }

    @Override
    public void delete(Integer activityId) {
        Optional<Activity> activity = get(activityId);
        if (activity.isPresent()) {
            activityRepository.delete(activity.get());
        }
    }
}
