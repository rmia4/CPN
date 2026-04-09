package com.example.proj.domain.post;

import com.example.proj.domain.category.CategoryService;
import com.example.proj.domain.comment.CommentModel;
import com.example.proj.domain.comment.CommentSaveRequestDto;
import com.example.proj.domain.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final CategoryService categoryService;

    // 게시글 리스트 페이지
    @GetMapping({"/list", "/list/"})
    public String postList(Model model) {
        List<PostModel> postList = postService.getAllPosts();
        model.addAttribute("postList", postList);
        return "pages/postTest";
    }

    @GetMapping("/add")
    public String addForm(Model model)
    {
        model.addAttribute("categoryList", categoryService.findAll());
        return "pages/postAdd";
    }


    @PostMapping("/add")
    public String postTestAdd(@Valid PostSaveRequestDto postSaveRequestDto,
                              Model model){
        System.out.println("들어옴: " + postSaveRequestDto.getTitle());
        postService.addPost(postSaveRequestDto);

        return "redirect:/post/list";
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


    @PostMapping("/comment/delete/{id}")
    public String commentDelete(@PathVariable("id") Long id,
                                @RequestParam("postId") String postId) {
        commentService.deleteCommentById(id);
        return "redirect:/post/detail/" + postId;
    }
}
