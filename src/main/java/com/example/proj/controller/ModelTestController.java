package com.example.proj.controller;

import com.example.proj.model.*;
import com.example.proj.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test/model")
public class ModelTestController {
    private final UserService userService;
    private final CommentService commentService;
    private final MapPinService mapPinService;
    private final NotificationService notificationService;
    private final PostService postService;

    @GetMapping("/modelTest")
    public String modelTest() {

        return "test/modelTest";
    }


    @PostMapping("/user/add")
    public String userTest(@RequestParam(name = "userId") String userId,
                           @RequestParam(name="userName") String  userName,
                           @RequestParam(name="userNumber") String userNumber,
                           Model model) {
        userService.addUser(userId, userName, userNumber);

        List<UserModel> userList = userService.findAll();

        model.addAttribute("userList",userList);
        return "modelView/userView";

    }

    @PostMapping("/notification/add")
    public String notificationTest(@RequestParam(name="userId")  String userId,
                                   @RequestParam(name = "description") String description,
                                   Model model) {
        notificationService.addNotification(userId, description);

        List<NotificationModel> list = notificationService.findAll();
        model.addAttribute("notificationList",list);

        return "modelView/notificationView";
    }

    @PostMapping("/mapPin/add")
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

    @PostMapping("/userPinNoti/delete")
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


        return "redirect:/test/model/modelTest";
    }

    @GetMapping("/userPinNoti/findAll")
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
        return "redirect:/test/model/modelTest";
    }

    @GetMapping("/postTest")
    public String postTest(Model model){
        List<PostModel> postList = postService.getAllPosts();
        model.addAttribute("postList",postList);

        return "test/postTest";
    }

    @PostMapping("/post/add")
    public String postTestAdd(@RequestParam(name = "title")String title,
                              @RequestParam(name="content") String content,
                              Model model){
        postService.addPost(title,content);

        return "redirect:/test/model/postTest";
    }

    @GetMapping("/post/detail/{id}")
    public String postTestDetail(@PathVariable("id") Long id, Model model){
        PostModel post = postService.getPostById(id);
        model.addAttribute("post",post);

        List<CommentModel> commentList = commentService.findAllByPostId(id);
        model.addAttribute("commentList",commentList);

        return "test/postDetail";
    }

    @PostMapping("post/delete/{id}")
    public String postTestDelete(@PathVariable("id") Long id){
        postService.deletePostById(id);

        return "redirect:/test/model/postTest";
    }


    @PostMapping("/comment/delete/{id}")
    public String postTestDeleteComment(@PathVariable("id") Long id,
                                        @RequestParam(name = "postId") String postId){
        commentService.deleteCommentById(id);

        return "redirect:/test/model/post/detail/" +postId;

    }

    @PostMapping("/comment/add/{postId}")
    public String postTestAddComment(@PathVariable("postId") Long postId,
                                     @RequestParam(name = "content") String content,
                                     Model model){
        commentService.addComment(content,postId,1L);   //로그인 옵션이 없어서 uesrId는 직접 입력으로

        return "redirect:/test/model/post/detail/" + String.valueOf(postId);
    }




}

