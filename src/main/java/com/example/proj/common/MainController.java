package com.example.proj.common;

import com.example.proj.domain.comment.CommentService;
import com.example.proj.domain.mapPin.MapPinService;
import com.example.proj.domain.notification.NotificationService;
import com.example.proj.domain.post.PostService;
import com.example.proj.domain.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final MapPinService mapPinService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/")
    public String home() {
        return "pages/home";
    }
}
