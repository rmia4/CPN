package com.example.proj.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MapPinModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private float lon;  //float로 수정
    @Column(nullable = false)
    private float lat;

    @Column(nullable = false)
    private String title;
    @Column(nullable = true)
    private String description;


    //여기에 userId 외래키도 추가해야 함


}
