package com.example.StreetLightFaultDetection.service;

import com.example.StreetLightFaultDetection.Entity.LightDetail;
import com.example.StreetLightFaultDetection.pojo.NotificationRequest;
import com.example.StreetLightFaultDetection.repository.LightDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private LightDetailRepository lightDetailRepository;

    @Autowired
    private FCMService fcmService;

    public String sendNotification( NotificationRequest request) throws ExecutionException, InterruptedException {
        fcmService.sendMessageToToken(request);
//        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
        return "success";
    }

    public String createLightDetail(LightDetail lightDetail){
        lightDetailRepository.save(lightDetail);
        return "saved";
    }

    public Page<LightDetail> getAllLightDetails(int page, int size, String[] sort, String key){
        Sort sorting = Sort.by(Sort.Direction.fromString(sort[1]), sort[0]);
        Pageable pageRequest = PageRequest.of(page, size, sorting);
        if (key==null){
            return lightDetailRepository.findAll(pageRequest);
        }
        return lightDetailRepository.search(key,pageRequest);
    }

    public Page<LightDetail> getFaultedLight(int page, int size, String[] sort, String key){
        Sort sorting = Sort.by(Sort.Direction.fromString(sort[1]), sort[0]);
        Pageable pageRequest = PageRequest.of(page, size, sorting);
        return lightDetailRepository.getFaultedLightByfault(pageRequest);
    }

    public String faultDetected(int id,boolean status) throws ExecutionException, InterruptedException {
        if(lightDetailRepository.existsById(id)) {
            Optional<LightDetail> lightDetail = lightDetailRepository.findById(id);
            LightDetail light=lightDetail.get();
//            boolean b1=Boolean.parseBoolean(status);
            light.setFault(status);
            lightDetailRepository.save(light);
            NotificationRequest request=new NotificationRequest();
            request.setBody("streetlight "+id+" is faulted");
            request.setTitle("streetLight fault");
            request.setToken("emSmlni9do-C_obt29EK8_:APA91bFnJ2hWce4hSdCJpqGfe2PRNfyIaI9EYYwEgUifmloxMsUpfkGsaCMYHxgNlxyq6pqnnfEtvqWf6_2HScJD0OxSLwzwA_ZGqdM_DUPYIBJy5WdKvPj14CTOhaLmYst4_4Y38X_l");
//            request.setTopic();
            fcmService.sendMessageToToken(request);
            return "success";
        }
        return "id does not exist";
    }

}
