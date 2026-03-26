package com.example.proj.controller;

import com.example.proj.model.MapPinModel;
import com.example.proj.model.NotificationModel;
import com.example.proj.model.UserModel;
import com.example.proj.service.MainService;
import com.example.proj.service.MapPinService;
import com.example.proj.service.NotificationService;
import com.example.proj.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    MainService mainService;
    UserService userService;
    NotificationService notificationService;
    MapPinService mapPinService;
    MainController(MainService mainService,  UserService userService,
                   NotificationService notificationService, MapPinService mapPinService) {
        this.mainService = mainService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.mapPinService = mapPinService;
    }

    @GetMapping("/modelTest")
    public String modelTest() {

        return "modelTest";
    }


    @PostMapping("/userModelTest")
    public String userTest(@RequestParam(name = "userId") String userId,
            @RequestParam(name="userName") String  userName,
            @RequestParam(name="userNumber") String userNumber,
            Model model) {
        userService.addUser(userId, userName, userNumber);

        List<UserModel> userList = userService.findAll();

        model.addAttribute("userList",userList);
        return "modelView/userView";

    }

    @PostMapping("/notificationModelTest")
    public String notificationTest(@RequestParam(name="userId")  String userId,
            @RequestParam(name = "description") String description,
            Model model) {
            notificationService.addNotification(userId, description);

            List<NotificationModel> list = notificationService.findAll();
            model.addAttribute("notificationList",list);

        return "modelView/notificationView";
    }

    @PostMapping("/mapPinModelTest")
    public String mapPinTest(@RequestParam(name="lon") float lon,
            @RequestParam(name = "lat") float lat,
            @RequestParam(name="title") String title,
            @RequestParam(name="description") String description,
            Model model) {

            mapPinService.addMapPin(lon, lat, title, description);
            List<MapPinModel> pinList = mapPinService.findAllMapPin();
            model.addAttribute("mapPinList",pinList);

        return "modelview/mapPinView";
    }

    @PostMapping("modelDelete")
    public String modelDelete(@RequestParam(name = "id") Long id,
                              @RequestParam(name ="modelType") String type) {
        if(type.equals("user")){
            userService.deleteUserById(id);
        }
        else if(type.equals("notification")){
            notificationService.deleteNotificationById(id);
        }
        else if(type.equals("mapPin")){
            mapPinService.deletePinById(id);

        }


        return "Redirect:/modelTest";
    }

    @GetMapping("modelTest/search")
    public String modelTest(@RequestParam(name="modelType") String type, Model model) {

        if(type.equals("user")){
            List<UserModel> userList = userService.findAll();
            model.addAttribute("userList",userList);
            return "modelView/userView";
        }
        else if(type.equals("notification")){
            List<NotificationModel> notificationList = notificationService.findAll();
            model.addAttribute("notificationList",notificationList);
            return "modelView/notificationView";
        }
        else if(type.equals("mapPin")){
            List<MapPinModel> mapPinList = mapPinService.findAllMapPin();
            model.addAttribute("mapPinList",mapPinList);
            return "modelview/mapPinView";

        }
        return "redirect:/modelTest";
    }



}
