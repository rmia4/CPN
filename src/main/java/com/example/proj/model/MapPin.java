package com.example.proj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MapPin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private long pinId;

    private int lon;
    private int lat;
    private String title;
    private String description;




}
