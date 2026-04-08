package com.example.proj.common;

import com.example.proj.domain.comment.CommentModel;
import com.example.proj.domain.comment.CommentSaveRequestDto;
import com.example.proj.domain.comment.CommentService;
import com.example.proj.domain.mapPin.MapPinModel;
import com.example.proj.domain.mapPin.MapPinService;
import com.example.proj.domain.notification.NotificationModel;
import com.example.proj.domain.notification.NotificationService;
import com.example.proj.domain.post.*;
import com.example.proj.domain.mapPin.MapPinSaveRequestDto;
import com.example.proj.domain.user.UserModel;
import com.example.proj.domain.user.UserSaveRequestDto;
import com.example.proj.domain.user.UserService;
import jakarta.validation.Valid;
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
    public String userTest(@Valid @RequestBody UserSaveRequestDto userSaveRequestDto,
                           Model model) {
        userService.addUser(userSaveRequestDto);

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
    public String mapPinTest(@RequestBody MapPinSaveRequestDto mapPinDto,
                             Model model) {

        mapPinService.addMapPin(mapPinDto);
        List<MapPinModel> pinList = mapPinService.findAllMapPin();
        model.addAttribute("mapPinList",pinList);

        return "modelView/mapPinView";
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
            return "modelView/mapPinView";

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
    public String postTestAdd(@Valid @RequestBody PostSaveRequestDto postSaveRequestDto,
                              Model model){
        postService.addPost(postSaveRequestDto);

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

    @PostMapping("/comment/add")
    public String postTestAddComment(@RequestBody CommentSaveRequestDto commentSaveRequestDto,
                                     Model model){
        commentService.addComment(commentSaveRequestDto);   //로그인 옵션이 없어서 uesrId는 직접 입력으로

        return "redirect:/test/model/post/detail/" + commentSaveRequestDto.getPostId();
    }




}

