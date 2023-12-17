package com.leadgen.backend.configuration;


import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.ApiKey;
import com.infobip.BaseUrl;
import com.infobip.api.SmsApi;
import com.infobip.model.SmsAdvancedTextualRequest;
import com.infobip.model.SmsDestination;
import com.infobip.model.SmsTextualMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class OtpConfiguration {
    @Value("${infobip.apiKey}")
    private String apiKey;

    @Value("${infobip.baseUrl}")
    private String baseUrl;

    public boolean sendSMS(String from, String to, String messageText) {
        var client = ApiClient.forApiKey(ApiKey.from(apiKey))
                .withBaseUrl(BaseUrl.from(baseUrl))
                .build();

        var api = new SmsApi(client);

        var smsMessage = new SmsTextualMessage()
                .addDestinationsItem(new SmsDestination().to(to))
                .text(messageText);

        var request = new SmsAdvancedTextualRequest()
                .messages(Collections.singletonList(smsMessage));

        try {
            var response = api.sendSmsMessage(request).execute();
            System.out.println(response);

            var reportsResponse = api.getOutboundSmsMessageDeliveryReports().execute();
            System.out.println(reportsResponse.getResults());
            return true;

        } catch (ApiException exception) {
            exception.printStackTrace();
            return false;
        }

}
}
