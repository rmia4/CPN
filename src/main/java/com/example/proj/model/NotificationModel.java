package com.example.proj.model;


import jakarta.persistence.*;

@Entity
public class NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long notificationId;


    @ManyToOne
    @JoinColumn(name = "userId")


}
