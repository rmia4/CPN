package com.example.proj.domain.post;

import com.example.proj.domain.post.category.CategoryService;
import com.example.proj.domain.post.comment.CommentModel;
import com.example.proj.domain.post.comment.CommentSaveRequestDto;
import com.example.proj.domain.post.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;


//TODO : bindingResult 사용한 검증단계 오류처리단계 추가
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
        model.addAttribute("categoryList", categoryService.findAll());
        return "pages/postTest";
    }
    //카테고리에 맞는 게시글 검색
    @GetMapping("/list/{category}")
    public String postListByCategory(@PathVariable("category") String category, Model model) {
        model.addAttribute("postList",postService.getPostsByCategory(category));
        model.addAttribute("categoryList", categoryService.findAll());

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
                              BindingResult bindingResult, Model model){
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


    @PostMapping("/delete")
    public String postDelete(@RequestParam("postId") Long postId) {
        postService.deletePostById(postId);
        return "redirect:/post/list";
    }

    @PostMapping("/{postId}/comment/add")
    public String postTestAddComment(@Valid CommentSaveRequestDto commentSaveRequestDto,
                                     @PathVariable("postId") Long postId,
                                     BindingResult bindingResult, Model model){
        commentService.addComment(commentSaveRequestDto,postId);

        //FIXME:리다이렉트 주소가 왜 test?
        return "redirect:/test/model/post/detail/" + postId;
    }


    @PostMapping("/{postId}/comment/delete")
    public String commentDelete(@RequestParam("id") Long id,
                                @PathVariable("postId") Long postId) {
        commentService.deleteCommentById(id);
        return "redirect:/post/detail/" + postId;
    }




}
