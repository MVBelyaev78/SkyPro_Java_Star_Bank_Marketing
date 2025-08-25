package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.auxiliary.NewCollection;
import org.skypro.starbank.marketing.component.recommendation.collect.RecommendationCollect;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.dto.recommendation.RecommendationServiceResult;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class RecommendationService {
    private final Collection<RecommendationCollect> recommendationCollects;

    public RecommendationService(Collection<RecommendationCollect> recommendationCollects) {
        this.recommendationCollects = recommendationCollects;
    }

    @Cacheable(value = "recommendationsCache", key = "#userId")
    public RecommendationServiceResult getServiceResult(UUID userId) {
        final Collection<Recommendation> recResult = new NewCollection<Recommendation>().initCollection();
        recommendationCollects
                .stream()
                .map(recCollect -> recCollect.getRecommendations(userId))
                .forEach(recResult::addAll);
        return new RecommendationServiceResult(userId, recResult);
    }
}

