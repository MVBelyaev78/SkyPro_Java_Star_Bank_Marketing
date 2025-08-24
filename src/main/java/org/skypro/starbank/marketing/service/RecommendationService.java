package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.component.dynamicrule.DynamicRecommendationRules;
import org.skypro.starbank.marketing.component.recommendation.RecommendationRule;
import org.skypro.starbank.marketing.configuration.dynamicrule.DynamicRulesDatabase;
import org.skypro.starbank.marketing.configuration.dynamicrule.DynamicRulesDatabaseEmulator;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.dto.recommendation.RecommendationServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {
    private final Collection<RecommendationRule> rules;
    private final DynamicRecommendationRules dynRecRules;

    @Autowired
    public RecommendationService(Collection<RecommendationRule> rules, DynamicRecommendationRules dynRecRules) {
        this.rules = rules;
        this.dynRecRules = dynRecRules;
    }

    public DynamicRecommendationRules getDynRecRules() {
        return dynRecRules;
    }

    public RecommendationServiceResult getServiceResult(UUID userId) {
        final Collection<Recommendation> recommendations = new HashSet<>();
        rules.forEach(recommendationRule -> recommendationRule.getRecommendation(userId)
                .ifPresent(recommendations::add));
        DynamicRulesDatabase dynamicRulesDB = new DynamicRulesDatabaseEmulator();
        dynamicRulesDB.getRules()
                .stream()
                .filter(dynamicRule -> dynRecRules.getSingleRecommendation(userId, dynamicRule).isPresent())
                .map(dynamicRule -> new Recommendation(
                    dynamicRule.getName(),
                    dynamicRule.getUuid(),
                    dynamicRule.getText()))
                .forEach(recommendations::add);
        return new RecommendationServiceResult(userId, recommendations);
    }
}
