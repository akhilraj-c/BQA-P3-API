package com.mindteck.common.service.impl;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.mindteck.common.service.FirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {
    @Override
    public String sendViaFcm(Notification notification, Map<String, String> payload, List<String> fcm) {
        try {
            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(notification)
                    .addAllTokens(fcm)
                    .build();

            ApiFuture<BatchResponse> response = FirebaseMessaging.getInstance().sendMulticastAsync(message);
            LOGGER.debug("Message published to token {} ", fcm);
            LOGGER.debug("Firebase  message response [FCM] {}", response);
            return response.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }    }
}
