package org.skypro.starbank.marketing.controller;

import org.skypro.starbank.marketing.model.recommendation.Recommendation;
import org.skypro.starbank.marketing.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public Collection<Recommendation> getRecommendationList(String userId) {
        return recommendationService.getRecommendationList(userId);
    }
}
