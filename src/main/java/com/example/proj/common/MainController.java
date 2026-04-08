package com.example.proj.common;

import com.example.proj.domain.mapPin.MapPinService;
import com.example.proj.domain.notification.NotificationService;
import com.example.proj.domain.comment.CommentService;
import com.example.proj.domain.post.PostService;
import com.example.proj.domain.user.UserService;
import org.springframework.stereotype.Controller;

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
