package com.example.proj.controller;

import com.example.proj.model.*;
import com.example.proj.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller

public class MainController {
    MainService mainService;
    UserService userService;
    NotificationService notificationService;
    MapPinService mapPinService;
    PostService postService;
    CommentService commentService;

    MainController(MainService mainService,  UserService userService,
                   NotificationService notificationService, MapPinService mapPinService,
                   PostService postService, CommentService commentService) {
        this.mainService = mainService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.mapPinService = mapPinService;
        this.postService = postService;
        this.commentService = commentService;
    }


}
