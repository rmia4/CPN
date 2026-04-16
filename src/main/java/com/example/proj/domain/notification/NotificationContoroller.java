package com.example.proj.domain.notification;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationContoroller {
    private final NotificationService notificationService;

    @PostMapping("/add")
    public String notificationAdd(@Valid NotificationSaveRequestDto dto,
                                  BindingResult bindingResult, Model model) {
        notificationService.addNotification(dto);


        return "";
    }

    @GetMapping("/list/{userId}")
    public String notificationList(@PathVariable("userId") String userId,
                                        Model model) {
        model.addAttribute("notificationList",notificationService.getAllNotificationByUserId(userId));

        return "";
    }

    //notification의 id로 알림 삭제
    @PostMapping("/delete")
    public String notificationDelete(@RequestParam("id") Long id) {
        notificationService.deleteNotificationById(id);
        //리턴값이 하나도 없어도 에러처리가 가능한가?

        return "";
    }


}
