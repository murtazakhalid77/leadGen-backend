package com.leadgen.backend.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import org.apache.http.impl.client.HttpClients;


import java.io.IOException;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Random;
@Component
public class HelperClass {

    public static String generateRandomOTP() {
        Random random = new Random();
        int otpLength = 6;
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    public static String formatPhoneNumber(String phoneNumber) {
        // Remove spaces from the phone number
        String formattedNumber = phoneNumber.replaceAll("\\s+", "");

        // If the number starts with "0", replace "0" with "92"
        if (formattedNumber.startsWith("0")) {
            formattedNumber = "92" + formattedNumber.substring(1);
        }

        return formattedNumber;
    }


    public static Map<String, Object> checkProfanity(String text) throws IOException {
            HttpClient httpClient = HttpClients.createDefault();
            String apiKey = "cv8JxBlWeKrdEjJeuChHuA==Lhv87IgPU2Tl3LKq";
            try {
                String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
                HttpGet request = new HttpGet("https://api.api-ninjas.com/v1/profanityfilter?text=" + encodedText);
                request.setHeader("X-Api-Key", apiKey);
                HttpResponse response = httpClient.execute(request);
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> resultMap = mapper.readValue(result, Map.class);
                    return resultMap;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    public static Map<String, Object> checkSentiment(String text) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        String apiKey = "cv8JxBlWeKrdEjJeuChHuA==Lhv87IgPU2Tl3LKq";
        try {
            String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
            HttpGet request = new HttpGet("https://api.api-ninjas.com/v1/sentiment?text=" + encodedText);
            request.setHeader("X-Api-Key", apiKey);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> resultMap = mapper.readValue(result, Map.class);
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
