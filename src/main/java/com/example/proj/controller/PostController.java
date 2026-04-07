package com.example.proj.controller;


import com.example.proj.dto.PostSaveRequestDto;
import com.example.proj.service.CommentService;
import com.example.proj.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;



    // 뷰 반환 부분은 일단 PASS
    @GetMapping("")
    public String finaAll(Model model){
        model.addAttribute("postList",postService.getAllPosts());


        return "";
    }


    @GetMapping("/add")
    public String addPost(@Valid @RequestBody PostSaveRequestDto postSaveRequestDto,
            BindingResult bindingResult,Model model){

        //DTO 매핑 에러 시
        if(bindingResult.hasErrors()){

            return "";
        }
        postService.addPost(postSaveRequestDto);


        return "";
    }



    @GetMapping("/detail/{postId}")
    public String detail(@PathVariable("postId") Long postId, Model model){
        model.addAttribute("postId",postService.getPostById(postId));
        model.addAttribute("commentList", commentService.findAllByPostId(postId));


        return "";
    }

    @PostMapping("/delete")
    public String deletePost(@RequestParam Long postId){
        postService.deletePostById(postId);


        return "";
    }

    @GetMapping("/category/{category}")
    public String findByCategory(@PathVariable String category, Model model){


        return "";
    }






}
