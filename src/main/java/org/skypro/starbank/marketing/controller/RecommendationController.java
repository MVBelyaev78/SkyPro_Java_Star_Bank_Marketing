package org.skypro.starbank.marketing.controller;

import org.skypro.starbank.marketing.recommendation.ProductRecommender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RecommendationController {
    private final List<ProductRecommender> recommenders;

    public RecommendationController(List<ProductRecommender> recommenders) {
        this.recommenders = recommenders;
    }

    @GetMapping("/{userId}")
    public List<ProductRecommender> getRecommendation(@PathVariable UUID userId) {
        return Collections.emptyList();
    }
}
