package com.example.StreetLightFaultDetection.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "LightDetail" , schema = "streetlight")
public class LightDetail {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "place")
    private String place;

    @Column(name = "lat")
    private float lat;

    @Column(name = "lng")
    private float lng;

    @Column(name = "fault")
    private boolean fault;
}
