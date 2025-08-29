package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.auxiliary.NewCollection;
import org.skypro.starbank.marketing.component.recommendation.collect.RecommendationCollect;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.dto.recommendation.RecommendationServiceResult;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {
    private final Collection<RecommendationCollect> recommendationCollects;

    public RecommendationService(Collection<RecommendationCollect> recommendationCollects) {
        this.recommendationCollects = recommendationCollects;
    }

    @Operation(summary = "Получить рекомендации",
            description = "Генерирует персонализированные рекомендации на основе фиксированных и динамических правил")
    public RecommendationServiceResult getServiceResult(@Parameter(description = "UUID пользователя", example = "123e4567-e89b-12d3-a456-426614174000")
                                                            UUID userId) {
        final Collection<Recommendation> recResult = new NewCollection<Recommendation>().initCollection();
        recommendationCollects
                .stream()
                .map(recCollect -> recCollect.getRecommendations(userId))
                .forEach(recResult::addAll);
        return new RecommendationServiceResult(userId, recResult);
    }
}
