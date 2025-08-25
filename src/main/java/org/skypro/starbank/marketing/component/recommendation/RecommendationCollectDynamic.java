package org.skypro.starbank.marketing.component.recommendation;

import org.skypro.starbank.marketing.component.dynamicrule.DynamicRecommendationRules;
import org.skypro.starbank.marketing.configuration.dynamicrule.DynamicRulesDatabase;
import org.skypro.starbank.marketing.configuration.dynamicrule.DynamicRulesDatabaseEmulator;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Component
public class RecommendationCollectDynamic implements RecommendationCollect {
    private final DynamicRecommendationRules dynRecRules;

    public RecommendationCollectDynamic(DynamicRecommendationRules dynRecRules) {
        this.dynRecRules = dynRecRules;
    }

    @Override
    public Collection<Recommendation> getRecommendations(UUID userId) {
        final Collection<Recommendation> recommendations = new HashSet<>();
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
                .forEach(recommendations::add);
        return recommendations;
    }
}
