package com.example.proj.domain.post;

import com.example.proj.domain.post.File.FileService;
import com.example.proj.domain.post.category.CategoryService;
import com.example.proj.domain.post.comment.CommentModel;
import com.example.proj.domain.post.comment.CommentSaveRequestDto;
import com.example.proj.domain.post.comment.CommentService;
import com.example.proj.domain.user.login.CustomUserDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


//TODO : bindingResult 사용한 검증단계 오류처리단계 추가
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final FileService fileService;

    // 게시글 리스트 페이지
    @GetMapping({"/list", "/list/"})
    public String postList(Model model) {
        List<PostModel> postList = postService.getAllPosts();
        model.addAttribute("postList", postList);
        model.addAttribute("categoryList", categoryService.findAll());
        return "pages/post/postList";
    }
    //카테고리에 맞는 게시글 검색
    @GetMapping("/list/{category}")
    public String postListByCategory(@PathVariable("category") String category, Model model) {
        model.addAttribute("postList",postService.getPostsByCategory(category));
        model.addAttribute("categoryList", categoryService.findAll());

        return "pages/post/postList";
    }


    @GetMapping("/add")
    public String addForm(Model model)
    {
        model.addAttribute("categoryList", categoryService.findAll());
        return "pages/post/postAdd";
    }


    @PostMapping("/add")
    public String postTestAdd(@Valid PostSaveRequestDto postSaveRequestDto, BindingResult bindingResult,
                              @AuthenticationPrincipal CustomUserDetail userDetail,
                              @RequestParam("image") List<MultipartFile> files,
                              Model model) throws IOException {
        postSaveRequestDto.setUserId(userDetail.getUsername());
        if (bindingResult.hasErrors()) {
            return "pages/post/postAdd";
        }
        // 이미지 저장
        List<String> imagePaths = fileService.saveFiles(files);
        // 기존 방식 유지 + 이미지 추가 전달
        postService.addPost(postSaveRequestDto, imagePaths);

        return "redirect:/post/list";
    }


    @GetMapping("/detail/{id}")
    public String postDetail(@PathVariable("id") Long id, Model model) {
        PostModel post = postService.getPostById(id);
        model.addAttribute("post", post);

        List<CommentModel> commentList = commentService.findAllByPostId(id);
        model.addAttribute("commentList", commentList);
        return "pages/post/postDetail";
    }


    @PostMapping("/delete")
    public String postDelete(@RequestParam("postId") Long postId) {
        postService.deletePostById(postId);
        return "redirect:/post/list";
    }

    @PostMapping("/{postId}/comment/add")
    public String addComment(@Valid CommentSaveRequestDto commentSaveRequestDto,
                             BindingResult bindingResult,
                             @PathVariable("postId") Long postId,
                             @AuthenticationPrincipal CustomUserDetail userDetail) {
        if (userDetail == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "redirect:/post/detail/" + postId;
        }

        commentService.addComment(commentSaveRequestDto, postId, userDetail.getUsername());

        return "redirect:/post/detail/" + postId;
    }


    @PostMapping("/{postId}/comment/delete")
    public String commentDelete(@RequestParam("id") Long id,
                                @PathVariable("postId") Long postId) {
        commentService.deleteCommentById(id);
        return "redirect:/post/detail/" + postId;
    }




}
