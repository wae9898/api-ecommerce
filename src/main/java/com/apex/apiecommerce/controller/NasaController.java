package com.apex.apiecommerce.controller;

import com.apex.apiecommerce.model.dto.NasaApodResponse;
import com.apex.apiecommerce.service.NasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/nasa")
public class NasaController {

    private final NasaService nasaService;

    @Autowired
    public NasaController(NasaService nasaService) {
        this.nasaService = nasaService;
    }

    @GetMapping("/apod")
    public Mono<ResponseEntity<NasaApodResponse>> getApod(
            @RequestParam(required = false) String date
    ) {
        return nasaService.getApod(date).map(ResponseEntity::ok);
    }

    @GetMapping("/random")
    public Mono<List<NasaApodResponse>> getRandomApods(@RequestParam Long count) {
        return nasaService.getRandomApods(count);
    }

    @GetMapping("/range")
    public Mono<List<NasaApodResponse>> getApodsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return nasaService.getApodsByDateRange(startDate, endDate);
    }
}
