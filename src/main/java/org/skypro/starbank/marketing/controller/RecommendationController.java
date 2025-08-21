package org.skypro.starbank.marketing.controller;

import org.skypro.starbank.marketing.service.RecommendationService;
import org.skypro.starbank.marketing.result.ServiceResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public ServiceResult getRecommendationList(@PathVariable String userId) {
        return recommendationService.getServiceResult(UUID.fromString(userId));
    }
}
