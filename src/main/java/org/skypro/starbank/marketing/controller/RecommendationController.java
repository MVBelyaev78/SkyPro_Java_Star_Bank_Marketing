package org.skypro.starbank.marketing.controller;

import org.skypro.starbank.marketing.service.RecommendationService;
import org.skypro.starbank.marketing.source.ServiceResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public ServiceResult getRecommendationList(String userId) {
        return recommendationService.getServiceREsult(userId);
    }
}
