package com.example.proj.domain.grok;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GrokController {
    private final GrokService grokService;


    @GetMapping("/grok")
    public String grokMain() {


        return "pages/grok/grokIndex";
    }

}
