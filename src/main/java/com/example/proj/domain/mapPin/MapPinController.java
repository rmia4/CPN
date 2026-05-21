package com.example.proj.domain.mapPin;

import com.example.proj.domain.post.File.FileService;
import com.example.proj.domain.post.PostModel;
import com.example.proj.domain.post.PostSaveRequestDto;
import com.example.proj.domain.post.PostService;
import com.example.proj.domain.post.category.CategoryService;
import com.example.proj.domain.post.comment.CommentModel;
import com.example.proj.domain.post.comment.CommentSaveRequestDto;
import com.example.proj.domain.post.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

//TODO: BindingResult 예외처리 추가
@Controller
@RequiredArgsConstructor
@RequestMapping("/mapPin")
public class MapPinController {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

    private final MapPinService  mapPinService;
    private final PostService postService;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final FileService fileService;

    @GetMapping("")
    public String mapView(Model model) {
        model.addAttribute("postPinList", postService.getLocatedPosts().stream()
                .map(this::toPinResponse)
                .toList());
        model.addAttribute("categoryList", categoryService.findAll());
        return "pages/map/mapPinView";
    }

    @PostMapping("/add")
    public ResponseEntity<PostPinResponse> addMapPin(@Valid MapPostRequestDto mapPostRequestDto,
                                                     @AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestParam(value = "image", required = false) List<MultipartFile> files) throws IOException {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        PostSaveRequestDto postDto = new PostSaveRequestDto();
        postDto.setTitle(mapPostRequestDto.getTitle());
        postDto.setContent(mapPostRequestDto.getDescription());
        postDto.setCategory(normalizeMapCategory(mapPostRequestDto.getCategory()));
        postDto.setLat(mapPostRequestDto.getLat());
        postDto.setLon(mapPostRequestDto.getLon());
        postDto.setUserId(userDetails.getUsername());
        postDto.setMeetingTime(mapPostRequestDto.getMeetingTime());

        PostModel post = postService.addPost(postDto, fileService.saveFiles(files == null ? List.of() : files));

        return ResponseEntity.ok(toPinResponse(post));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<MapPinCommentResponse>> comments(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(commentService.findAllByPostId(postId).stream()
                .map(this::toCommentResponse)
                .toList());
    }

    @PostMapping("/{postId}/comment/add")
    public ResponseEntity<MapPinCommentResponse> addComment(@PathVariable("postId") Long postId,
                                                            @RequestParam("content") String content,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        CommentSaveRequestDto dto = new CommentSaveRequestDto(content);
        CommentModel comment = commentService.addComment(dto, postId, userDetails.getUsername());
        return ResponseEntity.ok(toCommentResponse(comment));
    }

    @PostMapping("/delete")
    public String deleteMapPin(@RequestParam("mapPinId") Long mapPinId) {

        mapPinService.deletePinById(mapPinId);
        return "";
    }

    @GetMapping("/list")
    public String listMapPins(Model model,@RequestParam(name = "tag")String tag) {
        model.addAttribute("postPinList", postService.getLocatedPosts().stream()
                .filter(post -> Objects.equals(post.getCategory().getCategoryName(), tag))
                .map(this::toPinResponse)
                .toList());

        return "pages/map/mapPinView";
    }

    private String normalizeMapCategory(String category) {
        if ("lost".equals(category) || "found".equals(category) || "분실물".equals(category)) {
            return "분실물";
        }

        if ("matching".equals(category) || "밥친구".equals(category)) {
            return "밥친구";
        }

        return category;
    }

    private PostPinResponse toPinResponse(PostModel post) {
        return new PostPinResponse(
                post.getId(),
                post.getLat(),
                post.getLon(),
                post.getTitle(),
                post.getContent(),
                post.getCategory().getCategoryName(),
                getPinTag(post),
                formatDateTime(post.getMeetingTime()),
                post.getImages() == null || post.getImages().isEmpty() ? null : post.getImages().get(0).getImageUrl()
        );
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(DATE_TIME_FORMATTER);
    }

    private String getPinTag(PostModel post) {
        String title = post.getTitle() == null ? "" : post.getTitle();
        if (title.startsWith("[분실했어요]")) {
            return "분실했어요";
        }
        if (title.startsWith("[습득했어요]")) {
            return "습득했어요";
        }
        return post.getCategory().getCategoryName();
    }

    private MapPinCommentResponse toCommentResponse(CommentModel comment) {
        return new MapPinCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUser() == null ? "" : comment.getUser().getUserName()
        );
    }

    public record PostPinResponse(Long id, Float lat, Float lon, String title, String description, String category,
                                  String tag, String meetingTime, String imageUrl) {
    }

    public record MapPinCommentResponse(Long id, String content, LocalDateTime createdAt, String userName) {
    }

}
