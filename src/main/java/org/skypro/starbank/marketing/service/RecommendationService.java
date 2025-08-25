package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.auxiliary.NewCollection;
import org.skypro.starbank.marketing.component.recommendation.collect.RecommendationCollect;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.dto.recommendation.RecommendationServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.skypro.starbank.marketing.dto.Recommendation;
import org.skypro.starbank.marketing.result.ServiceResult;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class RecommendationService {
    private final Collection<RecommendationCollect> recommendationCollects;

    @Autowired
    public RecommendationService(Collection<RecommendationCollect> recommendationCollects) {
        this.recommendationCollects = recommendationCollects;
    @Cacheable(value = "recommendationsCache", key = "#userId")
    public ServiceResult getServiceResult(String userId) {
        final Collection<Recommendation> serviceResult = new ArrayList<>();
        serviceResult.add(new Recommendation("Yesterday",
                UUID.fromString("b9f1a8b5-35c5-4372-a83f-6f61e53c5c1a"),
                "I said something wrong, now I long for yesterday"));
        serviceResult.add(new Recommendation("Thank you for the music",
                UUID.fromString("65545241-06e3-4a1d-b7a5-8b0a7f9aa4e8"),
                "She says I began to sing long before I could talk"));
        return new ServiceResult(UUID.fromString(userId), serviceResult);
    }

    public RecommendationServiceResult getServiceResult(UUID userId) {
        final Collection<Recommendation> recResult = new NewCollection<Recommendation>().initCollection();
        recommendationCollects
                .stream()
                .map(recCollect -> recCollect.getRecommendations(userId))
                .forEach(recResult::addAll);
        return new RecommendationServiceResult(userId, recResult);
    }
}

