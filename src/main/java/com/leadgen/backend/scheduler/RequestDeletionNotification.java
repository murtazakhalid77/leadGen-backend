package com.leadgen.backend.scheduler;


import com.leadgen.backend.model.Category;
import com.leadgen.backend.model.PushNotificationRequest;
import com.leadgen.backend.model.User;
import com.leadgen.backend.model.UserRequest;
import com.leadgen.backend.repository.CategoryRepository;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.repository.UserRequestRepository;
import com.leadgen.backend.service.implementations.PushNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RequestDeletionNotification {

    @Autowired
    UserRequestRepository userRequestRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PushNotificationService pushNotificationService;
    Logger logger = LoggerFactory.getLogger(RequestDeletionNotification.class);
    public void notifySellers(UserRequest userRequest) {
        logger.info("Scheduler started");

        sendNotifications(userRequest, userRequest.getUser());
    }

    void sendNotifications(UserRequest userRequest, User user) {
        PushNotificationRequest pushNotificationRequest = PushNotificationRequest.builder()
                .title(userRequest.getTitle())
                .message("Your request has been deleted by the admin.")
                .token(user.getDeviceId())
                .build();

        try {
            pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
            logger.info("Notification sent to device: {}", user.getDeviceId());

            // Increment the notified number
            userRequest.setNotifiedNumber(userRequest.getNotifiedNumber() + 1);

//                         If notifiedNumber reaches 3, set notifiable to false
            if (userRequest.getNotifiedNumber() >= 3) {
                userRequest.setNotifiable(false);
            }

            userRequestRepository.save(userRequest);
        } catch (Exception e) {
            logger.error("Error sending notification to device: {}", user.getDeviceId(), e);
        }
    }

}

