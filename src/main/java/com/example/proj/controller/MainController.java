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


    /// //////////////////////////////////
    /// post test //// 컨트롤러 분리해야하는데 아직 안함
    ///
    @GetMapping("/test/postTest")
    public String postTest(Model model){
        List<PostModel> postList = postService.getAllPosts();
        model.addAttribute("postList",postList);

        return "test/postTest";
    }

    @PostMapping("/test/postTest/add")
    public String postTestAdd(@RequestParam(name = "title")String title,
            @RequestParam(name="content") String content,
            Model model){
        postService.addPost(title,content);

        return "redirect:/test/postTest";


    }

    @GetMapping("/test/postTest/readAll")
    public String postTestReadAll(Model model){
        List<PostModel> postList = postService.getAllPosts();
        model.addAttribute("postList",postList);


        return "test/postTest";
    }

    @GetMapping("/test/postTest/detail/{id}")
    public String postTestDetail(@PathVariable("id") Long id, Model model){
        PostModel post = postService.getPostById(id);
        model.addAttribute("post",post);

        List<CommentModel> commentList = commentService.findAllByPostId(id);
        model.addAttribute("commentList",commentList);

        return "test/postDetail";
    }

    @PostMapping("/test/postTest/delete/{id}")
    public String postTestDelete(@PathVariable("id") Long id){
        postService.deletePostById(id);

        return "redirect:/test/postTest";
    }


    //////////////////// comment test
    /// //////////////

    @PostMapping("/test/postTest/deleteComment/{id}")
    public String postTestDeleteComment(@PathVariable("id") Long id,
                                        @RequestParam(name = "postId")  Long postId){
        commentService.deleteCommentById(id);

        return "redirect:/test/postTest/detail/" + String.valueOf(id);

    }

    @PostMapping("/test/postTest/addComment/{postId}")
    public String postTestAddComment(@PathVariable("postId") Long postId,
            @RequestParam(name = "content") String content,
            Model model){
        commentService.addComment(content,postId,1L);   //로그인 옵션이 없어서 uesrId는 직접 입력으로

        return "redirect:/test/postTest/detail/" + String.valueOf(postId);
    }




}
