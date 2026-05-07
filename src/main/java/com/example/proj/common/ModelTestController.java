package com.example.proj.common;

import com.example.proj.domain.notification.NotificationSaveRequestDto;
import com.example.proj.domain.post.comment.CommentModel;
import com.example.proj.domain.post.comment.CommentSaveRequestDto;
import com.example.proj.domain.post.comment.CommentService;
import com.example.proj.domain.mapPin.MapPinModel;
import com.example.proj.domain.mapPin.MapPinSaveRequestDto;
import com.example.proj.domain.mapPin.MapPinService;
import com.example.proj.domain.notification.NotificationModel;
import com.example.proj.domain.notification.NotificationService;
import com.example.proj.domain.post.PostModel;
import com.example.proj.domain.post.PostSaveRequestDto;
import com.example.proj.domain.post.PostService;
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

        return "pages/test/modelTest";
    }


    @PostMapping("/user/add")
    public String userTest(@Valid UserSaveRequestDto userSaveRequestDto,
                           Model model) {
        userService.addUser(userSaveRequestDto);

        List<UserModel> userList = userService.findAll();

        model.addAttribute("userList",userList);
        return "pages/user/userView";

    }

    @PostMapping("/notification/add")
    public String notificationTest(@Valid NotificationSaveRequestDto dto,
                                   Model model) {
        notificationService.addNotification(dto);

        List<NotificationModel> list = notificationService.findAll();
        model.addAttribute("notificationList",list);

        return "pages/notification/notificationView";
    }

    @GetMapping("/mapPin")
    public String mapPinList(Model model){
        return "redirect:/mapPin";

    }


    @PostMapping("/mapPin/add")
    public String mapPinTest(@RequestBody MapPinSaveRequestDto mapPinDto,
                             Model model) {

        mapPinService.addMapPin(mapPinDto);
        List<MapPinModel> pinList = mapPinService.findAllMapPin();
        model.addAttribute("mapPinList",pinList);

        return "pages/map/mapPinView";
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
            return "pages/user/userView";
        }
        else if(type.equals("notification")){
            List<NotificationModel> notificationList = notificationService.findAll();
            model.addAttribute("notificationList",notificationList);
            return "pages/notification/notificationView";
        }
        else if(type.equals("mapPin")){
            List<MapPinModel> mapPinList = mapPinService.findAllMapPin();
            model.addAttribute("mapPinList",mapPinList);
            return "pages/map/mapPinView";

        }
        return "redirect:/test/model/modelTest";
    }

    @GetMapping("/postTest")
    public String postTest(Model model){
        List<PostModel> postList = postService.getAllPosts();
        model.addAttribute("postList",postList);

        return "pages/post/postList";
    }

//    @PostMapping("/post/add")
//    public String postTestAdd(@Valid @RequestBody PostSaveRequestDto postSaveRequestDto,
//                              Model model){
//        postService.addPost(postSaveRequestDto, );
//
//        return "redirect:/test/model/postTest";
//    }

    @GetMapping("/post/detail/{id}")
    public String postTestDetail(@PathVariable("id") Long id, Model model){
        PostModel post = postService.getPostById(id);
        model.addAttribute("post",post);

        List<CommentModel> commentList = commentService.findAllByPostId(id);
        model.addAttribute("commentList",commentList);

        return "pages/post/postDetail";
    }

    @PostMapping("post/delete/{id}")
    public String postTestDelete(@PathVariable("id") Long id){
        postService.deletePostByIdForTest(id);

        return "redirect:/test/model/postTest";
    }


    @PostMapping("/{postId}/comment/add")
    public String postTestAddComment(@PathVariable("postId") Long postId,
                                     CommentSaveRequestDto commentSaveRequestDto,
                                     Model model){
        commentService.addComment(commentSaveRequestDto, postId, null);   //로그인 옵션이 없어서 userId 연결 전

        return "redirect:/test/model/post/detail/" + postId;
    }

    @PostMapping("/comment/delete/{id}")
    public String postTestDeleteComment(@PathVariable("id") Long id,
                                        @RequestParam(name = "postId") String postId){
        commentService.deleteCommentById(id);

        return "redirect:/test/model/post/detail/" +postId;

    }


}
