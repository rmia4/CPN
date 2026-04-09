package com.example.proj.domain.post;

import com.example.proj.domain.comment.CommentModel;
import com.example.proj.domain.comment.CommentSaveRequestDto;
import com.example.proj.domain.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    // 게시글 리스트 페이지
    @GetMapping({"/list", "/list/"})
    public String postList(Model model) {
        List<PostModel> postList = postService.getAllPosts();
        model.addAttribute("postList", postList);
        return "pages/postTest";
    }


    @GetMapping("/add")
    public String addPost(@Valid @RequestBody PostSaveRequestDto postSaveRequestDto,
            BindingResult bindingResult,Model model){

        //DTO 매핑 에러 시
        if(bindingResult.hasErrors()){

            return "";
        }
        postService.addPost(postSaveRequestDto);

        return "redirect:/test/model/postTest";
    }

    @GetMapping("/detail/{id}")
    public String postDetail(@PathVariable("id") Long id, Model model) {
        PostModel post = postService.getPostById(id);
        model.addAttribute("post", post);

        List<CommentModel> commentList = commentService.findAllByPostId(id);
        model.addAttribute("commentList", commentList);
        return "pages/postDetail";
    }

    @PostMapping("/delete/{id}")
    public String postDelete(@PathVariable("id") Long id) {
        postService.deletePostById(id);
        return "redirect:/post/list";
    }

    @PostMapping("/comment/add")
    public String postTestAddComment(@RequestBody CommentSaveRequestDto commentSaveRequestDto,
                                     Model model){
        commentService.addComment(commentSaveRequestDto);   //로그인 옵션이 없어서 uesrId는 직접 입력으로

        return "redirect:/test/model/post/detail/" + commentSaveRequestDto.getPostId();
    }

    @GetMapping("/category/{category}")
    public String findByCategory(@PathVariable String category, Model model){


        return "";
    }






}
