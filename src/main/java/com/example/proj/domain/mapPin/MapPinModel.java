package com.example.proj.domain.mapPin;

import com.example.proj.domain.user.UserModel;
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

    //TODO: 테스트상 편의를 위해서 일단 nullable = true로
    @Column(nullable = true)
    private String tag;


    //TODO: nullable = false로 변경해야함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "userId",name = "userId",nullable = true)
    private UserModel user;


}
