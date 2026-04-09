package com.example.proj.domain.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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


    @GetMapping("/detail/{userId}")
    public String userDetail(@PathVariable("userId") String userId, Model model){
        model.addAttribute("user",userService.findByUserId(userId));

        return "";
    }



}