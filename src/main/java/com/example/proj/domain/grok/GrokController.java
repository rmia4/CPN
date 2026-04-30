package com.example.proj.domain.grok;


import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class GrokController {
    private final GrokService grokService;
    private final Dotenv dotenv;

    @GetMapping("/grok")
    public String grokMain(Model model) {

        model.addAttribute("api_key", dotenv.get("GROK_API_KEY"));

        return "pages/grok/grokIndex";
    }


}
