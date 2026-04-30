package com.example.proj.domain.grok.weather;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;




}
