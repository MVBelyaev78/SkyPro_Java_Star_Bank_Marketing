package org.skypro.starbank.marketing.service;

import lombok.AllArgsConstructor;
import org.skypro.starbank.marketing.dto.RecommendationProduct;
import org.skypro.starbank.marketing.dto.RecommendationResponse;
import org.skypro.starbank.marketing.service.rule.RecommendationRule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendationService {
    private final List<RecommendationRule> rules;

    public RecommendationResponse getRecommendations(UUID userId) {
        List<RecommendationProduct> recommendations = rules.stream()
                .map(rule -> rule.recommendation(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return new RecommendationResponse(userId, recommendations);
    }
}