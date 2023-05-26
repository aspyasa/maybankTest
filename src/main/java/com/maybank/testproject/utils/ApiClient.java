package com.maybank.testproject.utils;

import com.maybank.testproject.DTO.request.SearchDto;
import com.maybank.testproject.DTO.response.github.SearchResultDTO;
import com.maybank.testproject.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiClient {
    @Autowired
    public RestTemplate restTemplate;

    public static final String API_URL = "https://api.github.com";
    @Value("${api.token}")
    public String API_TOKEN;

    public SearchResultDTO searchUrl(SearchDto searchDto) {
        validateSearchDto(searchDto);

        String url = buildSearchUrl(searchDto);
        HttpHeaders headers = buildHttpHeaders();

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, SearchResultDTO.class).getBody();
    }

    private void validateSearchDto(SearchDto searchDto) {
        if (searchDto.getSearch() == null || searchDto.getSearch().isEmpty()) {
            throw new GlobalException("EMPTY_SEARCH", "Search cannot be empty");
        }
        if (searchDto.getPer_page() > 100) {
            throw new GlobalException("PER_PAGE_LIMIT_EXCEEDED", "Per page limit exceeded");
        }
    }

    private String buildSearchUrl(SearchDto searchDto) {
        return API_URL + "/search/users?q="
                + searchDto.getSearch() + "&sort="
                + searchDto.getSort() + "&order="
                + searchDto.getOrder() + "&per_page="
                + searchDto.getPer_page() + "&page="
                + searchDto.getPage();
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_TOKEN);
        headers.set("Accept", "application/vnd.github.v3+json");
        headers.set("X-GitHub-Api-Version", "2022-11-28");
        return headers;
    }
}
