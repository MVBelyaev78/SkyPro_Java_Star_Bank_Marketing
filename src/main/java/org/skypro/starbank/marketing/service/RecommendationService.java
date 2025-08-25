package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.component.dynamicrule.DynamicRecommendationRules;
import org.skypro.starbank.marketing.component.recommendation.RecommendationCollect;
import org.skypro.starbank.marketing.configuration.dynamicrule.DynamicRulesDatabase;
import org.skypro.starbank.marketing.configuration.dynamicrule.DynamicRulesDatabaseEmulator;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.dto.recommendation.RecommendationServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {
    private final DynamicRecommendationRules dynRecRules;
    private final Collection<RecommendationCollect> recommendationCollects;

    @Autowired
    public RecommendationService(DynamicRecommendationRules dynRecRules, Collection<RecommendationCollect> recommendationCollects) {
        this.dynRecRules = dynRecRules;
        this.recommendationCollects = recommendationCollects;
    }
    public RecommendationServiceResult getServiceResult(UUID userId) {
        final Collection<Recommendation> recResult = new HashSet<>();
        for (RecommendationCollect recCollect : recommendationCollects) {
            recResult.addAll(recCollect.getRecommendations(userId));
        }
        final DynamicRulesDatabase dynamicRulesDB = new DynamicRulesDatabaseEmulator();
        dynamicRulesDB.getRules()
                .stream()
                .filter(dynamicRule -> dynRecRules
                    .getSingleRecommendation(userId, dynamicRule)
                    .isPresent())
                .map(dynamicRule -> new Recommendation(
                    dynamicRule.getName(),
                    dynamicRule.getUuid(),
                    dynamicRule.getText()))
                .forEach(recResult::add);
        return new RecommendationServiceResult(userId, recResult);
    }
}
