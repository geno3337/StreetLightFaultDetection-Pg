package com.example.StreetLightFaultDetection.controller;

import com.example.StreetLightFaultDetection.Entity.LightDetail;
import com.example.StreetLightFaultDetection.pojo.NotificationRequest;
import com.example.StreetLightFaultDetection.pojo.NotificationResponse;
import com.example.StreetLightFaultDetection.service.FCMService;
import com.example.StreetLightFaultDetection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.ExecutionException;

@RestController
public class controller {

    @Autowired
    private Service service;

    @Autowired
    private FCMService fcmService;

    @PostMapping("/notification")
    public String sendNotification(@RequestBody NotificationRequest request) throws ExecutionException, InterruptedException {
        fcmService.sendMessageToToken(request);
//        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
        return "success";
    }

    @GetMapping(value = "/welcome")
    public String welcome(){
        return"welcome";
    }

    @PostMapping(value = "/createLightDetail")
    public String createLightDetail(@RequestBody LightDetail lightDetail){
        return service.createLightDetail(lightDetail);
    }

    @GetMapping(value = "/getAllLightDetail")
    public Page<LightDetail> getAllLightDetail(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "id,asc") String[] sort,
                                               @RequestParam(defaultValue = "") String key){
        return service.getAllLightDetails(page, size, sort, key);
    }

    @GetMapping(value = "/getFaultedLight")
    public Page<LightDetail> getFaultedLight(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id,asc") String[] sort,
                                             @RequestParam(defaultValue = "") String key){
        return service.getFaultedLight(page, size, sort, key);
    }

    @GetMapping(value = "/faultDetected")
    public String faultDetected(@RequestParam String id,@RequestParam boolean status) throws ExecutionException, InterruptedException {
        int intValue = Integer.parseInt(id);
        return service.faultDetected(intValue,status);
    }
}
