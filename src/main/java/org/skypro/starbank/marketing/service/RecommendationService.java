package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.auxiliary.NewCollection;
import org.skypro.starbank.marketing.component.recommendation.RecommendationCollect;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.dto.recommendation.RecommendationServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {
    private final Collection<RecommendationCollect> recommendationCollects;

    @Autowired
    public RecommendationService(Collection<RecommendationCollect> recommendationCollects) {
        this.recommendationCollects = recommendationCollects;
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
