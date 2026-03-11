package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;


    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routing;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {

        if(!userValidationService.validateUser(activityRequest.getUserId())){
            throw new RuntimeException("Invalid User: " + activityRequest.getUserId());
        }
        Activity activity =
                Activity.builder()
                        .userId(activityRequest.getUserId())
                        .type(activityRequest.getType())
                        .duration(activityRequest.getDuration())
                        .caloriesBurned(activityRequest.getCaloriesBurned())
                        .startTime(activityRequest.getStartTime())
                        .additionalMetrics(activityRequest.getAdditionalMetrics())
                        .build();
        Activity savedActivity = activityRepository.save(activity);
        //Publish to RabbitMQ for AI Processing
        try{
            rabbitTemplate.convertAndSend(exchange, routing, savedActivity);
        }catch(Exception e){
            log.error("Failed to publish activity to RabbitMQ: ", e);
        }
        return modelToResponse(savedActivity);
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        return activityRepository.findAllByUserId(userId)
                .stream()
                .map(this::modelToResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId) {
        return activityRepository.findById(activityId)
                .map(this::modelToResponse)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
    }

    private ActivityResponse modelToResponse(Activity activity) {
        ActivityResponse activityResponse = new ActivityResponse();

        activityResponse.setId(activity.getId());
        activityResponse.setUserId(activity.getUserId());
        activityResponse.setType(activity.getType());
        activityResponse.setDuration(activity.getDuration());
        activityResponse.setCaloriesBurned(activity.getCaloriesBurned());
        activityResponse.setStartTime(activity.getStartTime());
        activityResponse.setAdditionalMetrics(activity.getAdditionalMetrics());
        activityResponse.setCreatedAt(activity.getCreatedAt());
        activityResponse.setUpdatedAt(activity.getUpdatedAt());

        return activityResponse;
    }
}
