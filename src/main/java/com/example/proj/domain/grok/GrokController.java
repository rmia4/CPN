package com.example.proj.domain.grok;


import com.example.proj.domain.grok.weather.WeatherRequestDto;
import com.example.proj.domain.grok.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class GrokController {
    private final GrokService grokService;
    private final WeatherService weatherService;
    private final NaverShoppingService naverShoppingService;

    @GetMapping("/grok")
    public String grokMain(Model model) {
        WeatherRequestDto weatherData;
        String weatherSummary;

        try {
            weatherData = weatherService.getWeather(126.97F, 37.56F);
            weatherSummary = summarizeWeather(weatherData);
        } catch (Exception e) {
            weatherData = new WeatherRequestDto();
            weatherData.setBaseLocation("126.97 / 37.56");
            weatherData.setTargetDate("-");
            weatherSummary = "날씨 정보를 가져오지 못했습니다. 사용자가 입력한 상황을 중심으로 의상을 추천해주세요.";
        }

        model.addAttribute("weatherData", weatherData);
        model.addAttribute("weatherSummary", weatherSummary);

        return "pages/grok/grokIndex";
    }

    @PostMapping("/grok/chat")
    public ResponseEntity<GrokChatResponse> chat(@RequestBody GrokChatRequest request) {
        String message = request.message() == null || request.message().isBlank()
                ? "오늘 날씨에 맞는 기본 의상을 추천해줘"
                : request.message();
        String weatherSummary = request.weatherSummary() == null || request.weatherSummary().isBlank()
                ? "날씨 정보가 없습니다."
                : request.weatherSummary();

        try {
            GrokService.OutfitRecommendation recommendation = grokService.recommendOutfit(message, weatherSummary);
            return ResponseEntity.ok(new GrokChatResponse(
                    recommendation.answer(),
                    naverShoppingService.searchImages(recommendation.shoppingKeyword(), 6)
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new GrokChatResponse(e.getMessage(), List.of()));
        }
    }

    @PostMapping("/grok/outfit-check")
    public ResponseEntity<GrokChatResponse> checkOutfit(@RequestParam("image") MultipartFile image,
                                                        @RequestParam("weatherSummary") String weatherSummary) {
        String summary = weatherSummary == null || weatherSummary.isBlank()
                ? "날씨 정보가 없습니다."
                : weatherSummary;

        try {
            String answer = grokService.checkOutfitImage(image, summary);
            return ResponseEntity.ok(new GrokChatResponse(answer, List.of()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new GrokChatResponse(e.getMessage(), List.of()));
        }
    }

    private String summarizeWeather(WeatherRequestDto weatherData) {
        if (weatherData == null || weatherData.getHourlyWeather() == null || weatherData.getHourlyWeather().isEmpty()) {
            return "날씨 정보를 가져오지 못했습니다.";
        }

        WeatherRequestDto.WeatherInfo now = weatherData.getHourlyWeather().get(0);
        return "위치: " + weatherData.getBaseLocation()
                + ", 기준 예보: " + weatherData.getTargetDate()
                + ", 현재 기온: " + valueOrDash(now.getTemperature()) + "도"
                + ", 강수확률: " + valueOrDash(now.getRainPercent()) + "%"
                + ", 강수량: " + valueOrDash(now.getRainAmount())
                + ", 습도: " + valueOrDash(now.getHumidityPercent()) + "%"
                + ", 풍속: " + valueOrDash(now.getWindSpeed()) + "m/s";
    }

    private String valueOrDash(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }

    public record GrokChatRequest(String message, String weatherSummary) {
    }

    public record GrokChatResponse(String answer, List<String> images) {
    }
}
