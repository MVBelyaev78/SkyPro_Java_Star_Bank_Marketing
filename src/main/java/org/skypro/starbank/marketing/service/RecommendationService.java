package org.skypro.starbank.marketing.service;

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

    @Autowired
    public RecommendationService(Collection<RecommendationRule> rules) {
        this.rules = rules;
    }

    public RecommendationServiceResult getServiceResult(UUID userId) {
        final Collection<Recommendation> recommendations = new HashSet<>();
        rules.forEach(rule -> rule.getRecommendation(userId)
                .ifPresent(recommendations::add));
        DynamicRulesDatabase dynamicRulesDB = new DynamicRulesDatabaseEmulator();
        /*for (DynamicRule dynamicRule : dynamicRulesDB.getRules()) {

        }*/
        return new RecommendationServiceResult(userId, recommendations);
    }
}
