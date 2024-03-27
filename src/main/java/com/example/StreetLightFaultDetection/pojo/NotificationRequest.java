package com.example.StreetLightFaultDetection.pojo;

import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class NotificationRequest {
    private String title;
    private String body;
    private String topic;
    private String token;

}
