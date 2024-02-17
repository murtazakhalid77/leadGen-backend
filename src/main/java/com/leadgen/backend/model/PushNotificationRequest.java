package com.leadgen.backend.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PushNotificationRequest {
    private String title;
    private String message;
    private String topic;
    private String token;
    private String price;

}