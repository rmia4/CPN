package com.example.proj.domain.grok;


import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GrokService {
    private final Dotenv dotenv;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    public OutfitRecommendation recommendOutfit(String message, String weatherSummary, String gender, List<String> styles) {
        String apiKey = dotenv.get("GROQ_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GROQ_API_KEY가 설정되어 있지 않습니다.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String userProfile = buildUserProfile(gender, styles);

        Map<String, Object> body = Map.of(
                "model", "llama-3.3-70b-versatile",
                "stream", false,
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", """
                                        너는 캠퍼스 생활에 맞춘 의상 추천 assistant야.
                                        사용자의 날씨 정보와 요청을 바탕으로 오늘 입기 좋은 옷차림을 한국어로 짧고 실용적으로 추천해.
                                        답변에는 상의, 하의, 외투, 신발, 챙기면 좋은 물건을 포함해.
                                        답변은 반드시 한글, 숫자, 기본 문장부호만 사용해.
                                        한자, 일본어, 중국어, 영어 단어는 사용하지 마.
                                        브랜드명이나 외래어도 가능한 한글 표현으로 바꿔 써.
                                        답변 마지막 줄에는 네이버 쇼핑 이미지 검색에 쓸 검색어를 반드시 "검색어: " 형식으로 적어.
                                        검색어는 의류 품목 위주로 2개에서 4개만 한글로 적어.
                                        """
                        ),
                        Map.of(
                                "role", "user",
                                "content", "오늘 날씨 정보:\n" + weatherSummary
                                        + "\n\n사용자 정보:\n" + userProfile
                                        + "\n\n사용자 요청:\n" + message
                        )
                )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(
                    "https://api.groq.com/openai/v1/chat/completions",
                    entity,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException(
                    "Groq API 요청 실패 (" + e.getStatusCode() + "): " + e.getResponseBodyAsString(),
                    e
            );
        }

        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode content = root.path("choices").path(0).path("message").path("content");
        if (content.isMissingNode() || content.asText().isBlank()) {
            throw new IllegalStateException("Groq 응답을 읽을 수 없습니다.");
        }

        return parseRecommendation(content.asText());
    }

    private String buildUserProfile(String gender, List<String> styles) {
        StringBuilder userProfile = new StringBuilder();

        if (gender == null || gender.isBlank()) {
            userProfile.append("성별 정보 없음");
        } else {
            userProfile.append("성별: ").append(gender.trim());
        }

        List<String> cleanStyles = styles == null
                ? List.of()
                : styles.stream()
                .filter(style -> style != null && !style.isBlank())
                .map(String::trim)
                .toList();

        if (cleanStyles.isEmpty()) {
            userProfile.append("\n선호 스타일 정보 없음");
        } else {
            userProfile.append("\n선호 스타일: ").append(String.join(", ", cleanStyles));
        }

        return userProfile.toString();
    }

    public OutfitImageAnalysis checkOutfitImage(MultipartFile image, String weatherSummary) {
        String apiKey = dotenv.get("GROQ_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GROQ_API_KEY가 설정되어 있지 않습니다.");
        }

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("확인할 의상 이미지를 업로드해주세요.");
        }

        String mimeType = image.getContentType();
        if (mimeType == null || mimeType.isBlank()) {
            mimeType = "image/jpeg";
        }

        String imageData;
        try {
            imageData = Base64.getEncoder().encodeToString(image.getBytes());
        } catch (Exception e) {
            throw new IllegalStateException("이미지를 읽을 수 없습니다.", e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "model", "meta-llama/llama-4-scout-17b-16e-instruct",
                "stream", false,
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", List.of(
                                        Map.of(
                                                "type", "image_url",
                                                "image_url", Map.of(
                                                        "url", "data:" + mimeType + ";base64," + imageData
                                                )
                                        ),
                                        Map.of(
                                                "type", "text",
                                                "text", """
                                                        이 이미지에 있는 의상을 분석해줘.
                                                        옷의 종류, 색상, 두께감, 스타일을 설명하고 아래 날씨에 오늘 입기 적절한지 판단해줘.
                                                        적절하면 이유를 말하고, 부족하면 무엇을 더하거나 빼면 좋을지 추천해줘.
                                                        답변 마지막 줄에는 사용자 취향으로 저장할 스타일 키워드 3개를 반드시 "스타일키워드: " 형식으로 적어.
                                                        스타일 키워드는 한글 명사로만 작성하고 쉼표로 구분해. 예: 스타일키워드: 미니멀, 캐주얼, 데일리
                                                        답변은 반드시 한글, 숫자, 기본 문장부호만 사용해.
                                                        한자, 일본어, 중국어, 영어 단어는 사용하지 마.
                                                        
                                                        날씨 정보:
                                                        """ + weatherSummary
                                        )
                                )
                        )
                )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(
                    "https://api.groq.com/openai/v1/chat/completions",
                    entity,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException(
                    "Groq 이미지 분석 요청 실패 (" + e.getStatusCode() + "): " + e.getResponseBodyAsString(),
                    e
            );
        }

        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode content = root.path("choices").path(0).path("message").path("content");
        if (content.isMissingNode() || content.asText().isBlank()) {
            throw new IllegalStateException("Groq 이미지 분석 응답을 읽을 수 없습니다.");
        }

        return parseOutfitImageAnalysis(content.asText());
    }

    private OutfitImageAnalysis parseOutfitImageAnalysis(String rawAnswer) {
        List<String> lines = Arrays.stream(rawAnswer.split("\\R"))
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .toList();

        List<String> styleKeywords = List.of();
        StringBuilder answer = new StringBuilder();

        for (String line : lines) {
            if (isStyleKeywordLine(line)) {
                styleKeywords = parseStyleKeywords(line);
                continue;
            }

            if (!answer.isEmpty()) {
                answer.append("\n");
            }
            answer.append(line);
        }

        if (answer.isEmpty()) {
            answer.append(rawAnswer.trim());
        }

        return new OutfitImageAnalysis(
                removeUnsupportedCharacters(answer.toString()),
                styleKeywords
        );
    }

    private boolean isStyleKeywordLine(String line) {
        return line != null && line.replace(" ", "").startsWith("스타일키워드");
    }

    private List<String> parseStyleKeywords(String line) {
        String normalizedLine = line.replace('：', ':');
        int separatorIndex = normalizedLine.indexOf(':');
        if (separatorIndex < 0) {
            return List.of();
        }

        String keywordText = normalizedLine.substring(separatorIndex + 1);
        List<String> keywords = new ArrayList<>();
        for (String keyword : keywordText.split(",")) {
            String cleanedKeyword = removeUnsupportedCharacters(keyword)
                    .replaceAll("[^가-힣0-9 ]", "")
                    .trim();

            if (!cleanedKeyword.isBlank()) {
                keywords.add(cleanedKeyword);
            }
            if (keywords.size() == 3) {
                break;
            }
        }

        return keywords;
    }

    private OutfitRecommendation parseRecommendation(String rawAnswer) {
        List<String> lines = Arrays.stream(rawAnswer.split("\\R"))
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .toList();

        String shoppingKeyword = "오늘 의상";
        StringBuilder answer = new StringBuilder();

        for (String line : lines) {
            if (line.startsWith("검색어:")) {
                shoppingKeyword = line.substring("검색어:".length()).trim();
                continue;
            }

            if (!answer.isEmpty()) {
                answer.append("\n");
            }
            answer.append(line);
        }

        if (answer.isEmpty()) {
            answer.append(rawAnswer.trim());
        }

        return new OutfitRecommendation(
                removeUnsupportedCharacters(answer.toString()),
                removeUnsupportedCharacters(shoppingKeyword)
        );
    }

    private String removeUnsupportedCharacters(String text) {
        if (text == null) {
            return "";
        }

        return text
                .replaceAll("[\\p{IsHan}\\p{InHiragana}\\p{InKatakana}]", "")
                .replaceAll("[A-Za-z]", "")
                .replaceAll("[ ]{2,}", " ")
                .trim();
    }

    public record OutfitRecommendation(String answer, String shoppingKeyword) {
    }

    public record OutfitImageAnalysis(String answer, List<String> styleKeywords) {
    }
}
