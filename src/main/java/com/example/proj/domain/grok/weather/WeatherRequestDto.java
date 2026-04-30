package com.example.proj.domain.grok.weather;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WeatherRequestDto {
    private String targetDate;
    private String baseLocation;
    private List<WeatherInfo> hourlyWeather;

    public WeatherRequestDto() {
        hourlyWeather = new ArrayList<>();
        // 하루 24시간의 데이터를 저장하기 위한 초기화
        for (int j = 0; j < 24; j++) {
            hourlyWeather.add(new WeatherInfo());
        }
    }

    public WeatherInfo getInfo(int time) {
        if (time >= 0 && time < 24) {
            return hourlyWeather.get(time);
        }
        return null;
    }

    @Setter
    @Getter
    public static class WeatherInfo {
        private String rainPercent;
        private String rainAmount;
        private String humidityPercent;
        private String sky;
        private String temperature;
        private String windSpeed;
        private String maxTemp;
        private String minTemp;
    }

    

}
