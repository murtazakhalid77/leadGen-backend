package com.leadgen.backend.scheduler;


import com.leadgen.backend.model.Category;
import com.leadgen.backend.model.PushNotificationRequest;
import com.leadgen.backend.model.User;
import com.leadgen.backend.model.UserRequest;
import com.leadgen.backend.repository.CategoryRepository;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.repository.UserRequestRepository;
import com.leadgen.backend.service.implementations.FCMInitilizor;
import com.leadgen.backend.service.implementations.PushNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationScheduler {

    @Autowired
    UserRequestRepository userRequestRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PushNotificationService pushNotificationService;
    Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);
    @Scheduled(initialDelay = 5000, fixedDelay = 300000)
    private void notifySellers() {
        logger.info("Scheduler started");
        List<Category> categoriesList = categoryRepository.findAll();

        for (Category category : categoriesList) {
            List<UserRequest> userRequestList = userRequestRepository.findUserRequestsToNotify(category);
            List<User> userList = userRepository.findBySellingCategory(category);

            logger.info("Fetched user requests and users in the selling category");

            sendNotifications(userRequestList, userList);
        }
    }

    void sendNotifications(List<UserRequest> userRequestList, List<User> userList) {
        for (UserRequest userRequest : userRequestList) {
            if (userRequest.getNotifiedNumber() < 3 && userRequest.getNotifiable()) {
                for (User user : userList) {
                    PushNotificationRequest pushNotificationRequest = PushNotificationRequest.builder()
                            .title(userRequest.getTitle())
                            .message(userRequest.getDescription())
                            .price(userRequest.getPrice())
                            .token(user.getDeviceId())
                            .build();

                    try {
                        pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
                        logger.info("Notification sent to device: {}", user.getDeviceId());

                        // Increment the notified number
                        userRequest.setNotifiedNumber(userRequest.getNotifiedNumber() + 1);

                        // If notifiedNumber reaches 3, set notifiable to false
                        if (userRequest.getNotifiedNumber() >= 3) {
                            userRequest.setNotifiable(false);
                        }

                        // Save the updated UserRequest to the database
                        userRequestRepository.save(userRequest);
                    } catch (Exception e) {
                        logger.error("Error sending notification to device: {}", user.getDeviceId(), e);
                    }
                }
            }
        }
    }

}

