package com.apex.apiecommerce.service;

import com.apex.apiecommerce.model.dto.NasaApodResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface NasaService {

    Mono<NasaApodResponse> getApod(String date);

    Mono<List<NasaApodResponse>> getRandomApods(Long count);

    Mono<List<NasaApodResponse>> getApodsByDateRange(String startDate, String endDate);
}
