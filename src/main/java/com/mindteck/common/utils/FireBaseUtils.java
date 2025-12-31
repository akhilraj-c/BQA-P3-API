package com.mindteck.common.utils;

import com.google.firebase.messaging.Notification;
import com.mindteck.common.service.FirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
@Slf4j
public class FireBaseUtils {
    @Autowired
    FirebaseService fireBaseService;

    public String sendNotification(String title, String message, Map<String, String> payload, List<String> tokens) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(message)
                .build();

      //  String response = fireBaseService.sendViaFcm(notification, payload, tokens);
        return "";
    }
}
