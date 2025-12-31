package com.mindteck.common.service;

import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
public interface FirebaseService {
    String sendViaFcm(Notification notification, Map<String,String> payload , List<String> fcm);

}
