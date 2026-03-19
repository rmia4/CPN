package com.example.proj.controller;

import com.example.proj.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    MainController() {
        MainService service = new MainService();
    }

    @GetMapping("/index")
    public String index() {

        return "index";
    }




}
