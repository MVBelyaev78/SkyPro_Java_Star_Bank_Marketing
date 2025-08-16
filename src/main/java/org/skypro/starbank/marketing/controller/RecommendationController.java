package org.skypro.starbank.marketing.controller;

import lombok.AllArgsConstructor;
import org.skypro.starbank.marketing.dto.RecommendationResponse;
import org.skypro.starbank.marketing.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationService service;

    @GetMapping("/{userId}")
    public RecommendationResponse getRecommendations(@PathVariable UUID userId) {
        return service.getRecommendations(userId);
    }
}
