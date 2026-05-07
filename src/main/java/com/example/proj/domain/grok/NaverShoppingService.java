package com.example.proj.domain.grok;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NaverShoppingService {
    private final Dotenv dotenv;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<String> searchImages(String keyword, int display) {
        String clientId = dotenv.get("NAVER_CLIENT_ID");
        String clientSecret = dotenv.get("NAVER_CLIENT_SECRET");

        if (clientId == null || clientId.isBlank() || clientSecret == null || clientSecret.isBlank()) {
            return List.of();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        String url = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com/v1/search/shop.json")
                .queryParam("query", keyword)
                .queryParam("display", display)
                .queryParam("sort", "sim")
                .build(false)
                .toUriString();

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );

            JsonNode items = objectMapper.readTree(response.getBody()).path("items");
            List<String> images = new ArrayList<>();

            for (JsonNode item : items) {
                String image = item.path("image").asText();
                if (image != null && !image.isBlank()) {
                    images.add(image);
                }
            }

            return images;
        } catch (HttpClientErrorException e) {
            return List.of();
        }
    }
}
