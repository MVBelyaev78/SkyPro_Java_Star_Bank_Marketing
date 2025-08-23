package org.skypro.starbank.marketing.recommendation.service;

import org.skypro.starbank.marketing.recommendation.component.RecommendationRule;
import org.skypro.starbank.marketing.recommendation.dto.Recommendation;
import org.skypro.starbank.marketing.recommendation.dto.RecommendationServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {
    private final Collection<RecommendationRule> rules;

    @Autowired
    public RecommendationService(Collection<RecommendationRule> rules) {
        this.rules = rules;
    }

    public RecommendationServiceResult getServiceResult(UUID userId) {
        final Collection<Recommendation> recommendations = new ArrayList<>();
        rules.forEach(rule -> rule.getRecommendation(userId)
                .ifPresent(recommendations::add));
        return new RecommendationServiceResult(userId, recommendations);
    }
}
