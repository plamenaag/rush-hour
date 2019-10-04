package com.primeholding.rushhour.controller;

import com.primeholding.rushhour.entity.Activity;
import com.primeholding.rushhour.model.ActivityGetModel;
import com.primeholding.rushhour.model.ActivityPostModel;
import com.primeholding.rushhour.service.ActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private ActivityService activityService;
    private ModelMapper modelMapper;

    @Autowired
    public ActivityController(ActivityService activityService, ModelMapper modelMapper) {
        this.activityService = activityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public HttpEntity get(@PathVariable("id") Integer id) {
        Optional<Activity> activity = activityService.get(id);
        if (activity.isPresent()) {
            return ResponseEntity.ok(modelMapper.map(activity.get(), ActivityGetModel.class));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public HttpEntity get() {
        List<Activity> activities = activityService.get();
        List<ActivityGetModel> activityGetModels = new ArrayList<>();
        for (Activity activity : activities) {
            activityGetModels.add(modelMapper.map(activity, ActivityGetModel.class));
        }

        return ResponseEntity.ok(activityGetModels);
    }

    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity add(@RequestBody ActivityPostModel activityPostModel) {
        Activity activity = modelMapper.map(activityPostModel, Activity.class);

        Optional<Activity> savedActivity = activityService.add(activity);
        if (savedActivity.isPresent()) {
            return ResponseEntity.ok(modelMapper.map(savedActivity.get(), ActivityGetModel.class));
        }

        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    @PutMapping("/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity update(@PathVariable(required = true) Integer id, @RequestBody ActivityPostModel activityPostModel) {
        Activity activity = modelMapper.map(activityPostModel, Activity.class);
        Optional<Activity> updatedActivity = activityService.update(id, activity);
        if (updatedActivity.isPresent()) {
            return ResponseEntity.ok(modelMapper.map(updatedActivity.get(), ActivityGetModel.class));
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity delete(@PathVariable(required = true) Integer id) {
        if (activityService.get(id).isPresent()) {
            activityService.delete(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
