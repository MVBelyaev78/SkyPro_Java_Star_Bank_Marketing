package org.skypro.starbank.marketing.component.recommendation.collect;

import org.skypro.starbank.marketing.auxiliary.NewCollection;
import org.skypro.starbank.marketing.component.dynamicrule.DynamicRecommendationRules;
import org.skypro.starbank.marketing.configuration.dynamicrule.DynamicRulesDatabase;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;

@Component
public class RecommendationCollectDynamic implements RecommendationCollect {
    private final DynamicRecommendationRules dynRecRules;
    private final DynamicRulesDatabase dynamicRulesDatabase;

    public RecommendationCollectDynamic(DynamicRecommendationRules dynRecRules, DynamicRulesDatabase dynamicRulesDatabase) {
        this.dynRecRules = dynRecRules;
        this.dynamicRulesDatabase = dynamicRulesDatabase;
    }

    @Override
    public Collection<Recommendation> getRecommendations(UUID userId) {
        final Collection<Recommendation> recommendations = new NewCollection<Recommendation>().initCollection();
        dynamicRulesDatabase
                .getRules()
                .stream()
                .filter(dynamicRule -> dynRecRules
                        .getSingleRecommendation(userId, dynamicRule)
                        .isPresent())
                .map(dynamicRule -> new Recommendation(
                        dynamicRule.name(),
                        dynamicRule.uuid(),
                        dynamicRule.text()))
                .forEach(recommendations::add);
        return recommendations;
    }
}
