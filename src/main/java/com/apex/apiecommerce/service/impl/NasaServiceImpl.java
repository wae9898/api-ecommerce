package com.apex.apiecommerce.service.impl;

import com.apex.apiecommerce.model.dto.NasaApodResponse;
import com.apex.apiecommerce.service.NasaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class NasaServiceImpl implements NasaService {

    private final String apiKey;

    private final WebClient webClient;

    public NasaServiceImpl(
            WebClient.Builder webClientBuilder,
            @Value("${nasa.api.key}") String apiKey,
            @Value("${nasa.api.base-url}") String nasaApiBaseUrl) {
        this.apiKey = apiKey;
        this.webClient = webClientBuilder
                .baseUrl(nasaApiBaseUrl)
                .build();
    }


    public Mono<NasaApodResponse> getApod(String date) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/planetary/apod")
                        .queryParam("api_key", apiKey)
                        .queryParam("date", date)
                        .build())
                .retrieve()
                .bodyToMono(NasaApodResponse.class);
    }

    public Mono<List<NasaApodResponse>> getRandomApods(Long count) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/planetary/apod")
                        .queryParam("api_key", apiKey)
                        .queryParam("count", count)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<NasaApodResponse>>() {});
    }

    public Mono<List<NasaApodResponse>> getApodsByDateRange(String startDate, String endDate) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/planetary/apod")
                        .queryParam("api_key", apiKey)
                        .queryParam("start_date", startDate)
                        .queryParam("end_date", endDate)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<NasaApodResponse>>() {});
    }

}
