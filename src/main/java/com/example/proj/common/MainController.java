package com.example.proj.common;

import com.example.proj.domain.post.comment.CommentService;
import com.example.proj.domain.mapPin.MapPinService;
import com.example.proj.domain.notification.NotificationService;
import com.example.proj.domain.post.PostService;
import com.example.proj.domain.user.UserModel;
import com.example.proj.domain.user.UserService;
import com.example.proj.domain.user.login.CustomUserDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "pages/home/home";
    }

//    @GetMapping("/timetable")
//    public String timetable() { return "pages/timetable"; }

    @GetMapping("/login")
    public String login() {
        return "pages/auth/login";
    }

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal CustomUserDetail userDetail,
            Model model) {

        UserModel user = userDetail.getUserModel();
        model.addAttribute("user", user);

        return "pages/admin/adminTest";
    }
}
