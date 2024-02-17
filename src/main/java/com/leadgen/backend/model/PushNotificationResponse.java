package com.leadgen.backend.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PushNotificationResponse {
        private int status;
        private String message;

    }
