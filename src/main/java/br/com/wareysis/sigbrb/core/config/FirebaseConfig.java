package br.com.wareysis.sigbrb.core.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.ErrorCode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;

import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.PostConstruct;

@Configuration
@Slf4j
public class FirebaseConfig {

    @Value("${firebase.service-account.path}")
    private String serviceAccountPath;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void firebaseInitialize() throws FirebaseException {

        try {

            Resource resource = resourceLoader.getResource("classpath:" + serviceAccountPath);

            InputStream serviceAccountCredentials = resource.getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccountCredentials))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {

                FirebaseApp.initializeApp(options);
                log.info("FIREBASE: Firebase App initialized");

            }

        } catch (IOException e) {
            throw new FirebaseException(ErrorCode.INTERNAL, "FIREBASE: Error initializing firebase" + e.getMessage(), null);
        }

    }
}
