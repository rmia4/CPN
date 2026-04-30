package com.example.proj.domain.grok.weather;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;


    @GetMapping("/w")
    public String w(
//            @RequestParam(name = "lon") float lon,
//            @RequestParam(name="lat")  float lat,
            Model model) {
        //테스트용 값 집어넣기

        WeatherRequestDto weather =  weatherService.getWeather(126.97F ,37.56F);
        model.addAttribute("weatherData", weather);



        return "pages/grok/weather";
    }



}
