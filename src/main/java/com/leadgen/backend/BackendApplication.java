package com.leadgen.backend;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	FirebaseMessaging firebaseMessaging () throws IOException {
		GoogleCredentials googleCredentials = GoogleCredentials
				.fromStream( new ClassPathResource("lead-2e832-firebase-adminsdk-z3xuo-0b6c27afd0.json").getInputStream());
		FirebaseOptions firebaseOptions = FirebaseOptions.builder ()
				.setCredentials (googleCredentials).build();
		FirebaseApp app = FirebaseApp.initializeApp (firebaseOptions,"Lead-Gen");
		return FirebaseMessaging.getInstance (app);
	}

}
