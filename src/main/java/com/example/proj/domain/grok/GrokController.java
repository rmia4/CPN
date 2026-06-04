package com.example.proj.domain.grok;


import com.example.proj.domain.grok.weather.WeatherRequestDto;
import com.example.proj.domain.grok.weather.WeatherService;
import com.example.proj.domain.user.UserModel;
import com.example.proj.domain.user.UserService;
import com.example.proj.domain.user.login.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final UserService userService;

    @GetMapping("/grok")
    public String grokMain(Model model, @AuthenticationPrincipal CustomUserDetail userDetail) {
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
        model.addAttribute("userGender", getProfileGender(userDetail));

        return "pages/grok/grokIndex";
    }

    @PostMapping("/grok/chat")
    public ResponseEntity<GrokChatResponse> chat(@RequestBody GrokChatRequest request,
                                                 @AuthenticationPrincipal CustomUserDetail userDetail) {
        String message = request.message() == null || request.message().isBlank()
                ? "오늘 날씨에 맞는 기본 의상을 추천해줘"
                : request.message();
        String weatherSummary = request.weatherSummary() == null || request.weatherSummary().isBlank()
                ? "날씨 정보가 없습니다."
                : request.weatherSummary();
        UserModel profileUser = getProfileUser(userDetail);
        String profileGender = profileUser == null ? null : normalizeGender(profileUser.getGender());
        String gender = request.gender() == null || request.gender().isBlank()
                ? profileGender
                : request.gender();
        List<String> styles = getProfileStyles(profileUser);

        try {
            GrokService.OutfitRecommendation recommendation = grokService.recommendOutfit(message, weatherSummary, gender, styles);
            return ResponseEntity.ok(new GrokChatResponse(
                    recommendation.answer(),
                    naverShoppingService.searchImages(recommendation.shoppingKeyword(), gender, 6)
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new GrokChatResponse(e.getMessage(), List.of()));
        }
    }

    @PostMapping("/grok/outfit-check")
    public ResponseEntity<GrokChatResponse> checkOutfit(@RequestParam("image") MultipartFile image,
                                                        @RequestParam("weatherSummary") String weatherSummary,
                                                        @AuthenticationPrincipal CustomUserDetail userDetail) {
        String summary = weatherSummary == null || weatherSummary.isBlank()
                ? "날씨 정보가 없습니다."
                : weatherSummary;

        try {
            GrokService.OutfitImageAnalysis analysis = grokService.checkOutfitImage(image, summary);
            List<String> styleKeywords = analysis.styleKeywords();
            boolean styleSaved = false;

            if (userDetail != null && styleKeywords.size() == 3) {
                UserModel updatedUser = userService.updateStyleKeywords(userDetail.getUsername(), styleKeywords);
                userDetail.getUserModel().setStyle1(updatedUser.getStyle1());
                userDetail.getUserModel().setStyle2(updatedUser.getStyle2());
                userDetail.getUserModel().setStyle3(updatedUser.getStyle3());
                styleSaved = true;
            }

            return ResponseEntity.ok(new GrokChatResponse(analysis.answer(), List.of(), styleKeywords, styleSaved));
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

    private String getProfileGender(CustomUserDetail userDetail) {
        UserModel user = getProfileUser(userDetail);
        if (user == null) {
            return "";
        }

        return normalizeGender(user.getGender());
    }

    private UserModel getProfileUser(CustomUserDetail userDetail) {
        if (userDetail == null || userDetail.getUsername() == null || userDetail.getUsername().isBlank()) {
            return null;
        }

        return userService.findByUserId(userDetail.getUsername());
    }

    private List<String> getProfileStyles(UserModel user) {
        if (user == null) {
            return List.of();
        }

        return List.of(user.getStyle1(), user.getStyle2(), user.getStyle3()).stream()
                .filter(style -> style != null && !style.isBlank())
                .map(String::trim)
                .toList();
    }

    private String normalizeGender(String gender) {
        if (gender == null || gender.isBlank()) {
            return "";
        }

        String trimmedGender = gender.trim();
        if (trimmedGender.contains("남")) {
            return "남성";
        }
        if (trimmedGender.contains("여")) {
            return "여성";
        }
        if (trimmedGender.contains("기타")) {
            return "기타";
        }

        return trimmedGender;
    }

    public record GrokChatRequest(String message, String weatherSummary, String gender) {
    }

    public record GrokChatResponse(String answer, List<String> images, List<String> styles, boolean styleSaved) {
        public GrokChatResponse(String answer, List<String> images) {
            this(answer, images, List.of(), false);
        }
    }
}
