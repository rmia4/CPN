package com.example.proj.domain.user;


import com.example.proj.domain.mapPin.MapPinModel;
import com.example.proj.domain.notification.NotificationModel;
import com.example.proj.domain.post.PostModel;
import com.example.proj.domain.timeTable.TimeTableModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String userNumber;

    @Column
    private String gender;

    @Column
    private String role;

    @Column(nullable = false)
    private String passwd;

    private Float mannerPoint;

    @Column(nullable = true)
    private String style1;
    @Column(nullable = true)
    private String style2;
    @Column(nullable = true)
    private String style3;

    //양방향 매핑
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationModel> notificationList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostModel> postList;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MapPinModel>  mapPinList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeTableModel>  timeTableList;


}
