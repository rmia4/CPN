package com.example.proj.domain.user;


import com.example.proj.domain.user.login.CustomUserDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/add")
    public String userAdd(@Valid @RequestBody UserSaveRequestDto userSaveRequestDto
            , BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){

            //오류 시

        }
        userService.addUser(userSaveRequestDto);


        return "";
    }

    @PostMapping("/delete")
    public String userDelete(@RequestParam("userId") String userId){
        userService.deleteUserByUserId(userId);

        return "";
    }


    @GetMapping("/detail")
    public String userDetail(@AuthenticationPrincipal CustomUserDetail userDetail, Model model){
        model.addAttribute("user", userService.findByUserId(userDetail.getUsername()));
        model.addAttribute("userUpdateRequestDto", new UserUpdateRequestDto());

        return "pages/user/userDetail";
    }

    @PostMapping("/detail")
    public String userUpdate(@AuthenticationPrincipal CustomUserDetail userDetail,
                             @Valid UserUpdateRequestDto userUpdateRequestDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userService.findByUserId(userDetail.getUsername()));
            return "pages/user/userDetail";
        }

        UserModel updatedUser = userService.updateUser(userDetail.getUsername(), userUpdateRequestDto);
        refreshPrincipal(updatedUser);

        return "redirect:/user/detail";
    }

    private void refreshPrincipal(UserModel user) {
        CustomUserDetail updatedPrincipal = new CustomUserDetail(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                updatedPrincipal,
                updatedPrincipal.getPassword(),
                updatedPrincipal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }



}
