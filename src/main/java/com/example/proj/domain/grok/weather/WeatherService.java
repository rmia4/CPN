package com.example.proj.domain.grok.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.springframework.web.util.UriComponentsBuilder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class WeatherService {
    private RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;
    private final Dotenv dotenv;



    public WeatherRequestDto getWeather(float lon, float lat) {
        Map<String, Integer> grid = lonLatToGrid(lon, lat);

        String[] baseDateTime = getLatestBaseDateTime();
        String baseDate = baseDateTime[0];
        String baseTime = baseDateTime[1];

        String response = getApi(grid, baseDate, baseTime);
        WeatherRequestDto result = new WeatherRequestDto();

        JsonNode root = objectMapper.readTree(response);
        JsonNode itemArray = root.path("response").path("body").path("items").path("item");

        if (itemArray.isMissingNode() || !itemArray.isArray() || itemArray.size() == 0) {
            System.out.println("api response error or empty \n" + response);
            return result;
        }

        result.setBaseLocation(lon + " / " + lat);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 응답 데이터 중 가장 빠른 예보 시간(시작점)을 파악하기 위한 변수
        LocalDateTime startForecastDateTime = null;

        for (JsonNode item : itemArray) {
            String fcstDate = item.path("fcstDate").asText();
            String fcstTime = item.path("fcstTime").asText();
            String category = item.path("category").asText();
            String fcstValue = item.path("fcstValue").asText();

            // 현재 항목의 예보 일시(LocalDateTime) 생성
            LocalDate dateObj = LocalDate.parse(fcstDate, dateFormatter);
            int hour = Integer.parseInt(fcstTime) / 100;
            LocalDateTime currentForecastDateTime = dateObj.atTime(hour, 0);

            // 첫 번째 루프에서 시작 시점을 저장
            if (startForecastDateTime == null) {
                startForecastDateTime = currentForecastDateTime;
                // 결과 객체에 시작 날짜 기록 (선택 사항)
                result.setTargetDate(fcstDate + " " + fcstTime);
            }

            // 시작 시점으로부터 몇 시간이 지났는지 계산 (이 값이 곧 배열의 인덱스가 됨)
            int hourDifference = (int) ChronoUnit.HOURS.between(startForecastDateTime, currentForecastDateTime);

            // 24시간(인덱스 0~23)을 초과하는 데이터는 무시
            if (hourDifference >= 24) {
                continue;
            }

            // 해당 시간차(인덱스)에 맞는 객체 가져오기
            WeatherRequestDto.WeatherInfo info = result.getInfo(hourDifference);
            if (info == null) continue;

            switch (category) {
                case "POP": info.setRainPercent(fcstValue); break;
                case "PCP": info.setRainAmount(fcstValue); break;
                case "REH": info.setHumidityPercent(fcstValue); break;
                case "SKY": info.setSky(fcstValue); break;
                case "TMP": info.setTemperature(fcstValue); break;
                case "WSD": info.setWindSpeed(fcstValue); break;
                case "TMN": info.setMinTemp(fcstValue); break;
                case "TMX": info.setMaxTemp(fcstValue); break;
            }
        }

        return result;
    }
    //날씨 요청 시간 반환함수
    private String[] getLatestBaseDateTime() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();

        LocalDate baseDate = now.toLocalDate();
        String baseTime;

        // API 제공 시간은 각 발표시각 10분 이후[cite: 4]
        if (hour < 2 || (hour == 2 && minute < 10)) {
            baseDate = baseDate.minusDays(1);
            baseTime = "2300";
        } else if (hour < 5 || (hour == 5 && minute < 10)) {
            baseTime = "0200";
        } else if (hour < 8 || (hour == 8 && minute < 10)) {
            baseTime = "0500";
        } else if (hour < 11 || (hour == 11 && minute < 10)) {
            baseTime = "0800";
        } else if (hour < 14 || (hour == 14 && minute < 10)) {
            baseTime = "1100";
        } else if (hour < 17 || (hour == 17 && minute < 10)) {
            baseTime = "1400";
        } else if (hour < 20 || (hour == 20 && minute < 10)) {
            baseTime = "1700";
        } else if (hour < 23 || (hour == 23 && minute < 10)) {
            baseTime = "2000";
        } else {
            baseTime = "2300";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return new String[]{baseDate.format(formatter), baseTime};
    }

    public String getApi(Map<String, Integer> grid, String baseDate, String baseTime) {
        String url = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("serviceKey", dotenv.get("WEATHER_API_KEY"))
                .queryParam("pageNo", 1)
                // 24시간 데이터를 확보하기 위해 충분한 행 수 지정 (요소 개수 * 24시간 + 여유분)
                .queryParam("numOfRows", 300)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", grid.get("x"))
                .queryParam("ny", grid.get("y"));

        url = builder.build(false).toUriString();

        ResponseEntity<String> rs = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String.class
        );
        return rs.getBody();
    }

    //정렬



    public Map<String, Integer> lonLatToGrid(double lonDeg, double latDeg) {
        // 기존 제공된 격자 변환 로직 동일 유지
        final double RE = 6371.00877;
        final double GRID = 5.0;
        final double SLAT1 = 30.0;
        final double SLAT2 = 60.0;
        final double OLON = 126.0;
        final double OLAT = 38.0;
        final double XO = 210.0 / GRID;
        final double YO = 675.0 / GRID;

        final double PI = Math.asin(1.0) * 2.0;
        final double DEGRAD = PI / 180.0;

        final double re = RE / GRID;
        final double slat1 = SLAT1 * DEGRAD;
        final double slat2 = SLAT2 * DEGRAD;
        final double olon = OLON * DEGRAD;
        final double olat = OLAT * DEGRAD;

        double sn = Math.tan(PI * 0.25 + slat2 * 0.5) / Math.tan(PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);

        double sf = Math.tan(PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;

        double ro = Math.tan(PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        double ra = Math.tan(PI * 0.25 + latDeg * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);

        double theta = lonDeg * DEGRAD - olon;
        if (theta > PI) theta -= 2.0 * PI;
        if (theta < -PI) theta += 2.0 * PI;
        theta *= sn;

        double x = ra * Math.sin(theta) + XO;
        double y = ro - ra * Math.cos(theta) + YO;

        Map<String, Integer> grid = new HashMap<>();
        grid.put("x", (int) (x + 1.5));
        grid.put("y", (int) (y + 1.5));

        return grid;
    }




}
