package com.mindteck.common.config;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
@Configuration
@Slf4j
public class firebaseConfig {
    @Value("${mindteck.google.service.file}")
    private String serviceFile;

    @Bean
    public void initializeFireBase() {

        try {
            final FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(serviceFile).getInputStream()))
                    .build();
            if(FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(firebaseOptions);
                LOGGER.info("Firebase initialized successfully .");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
