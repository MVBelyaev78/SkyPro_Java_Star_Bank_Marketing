package org.skypro.starbank.marketing.component.recommendation.collect;

import org.skypro.starbank.marketing.auxiliary.NewCollection;
import org.skypro.starbank.marketing.component.dynamicrule.common.DynamicRecommendationRules;
import org.skypro.starbank.marketing.repository.DynamicRepository;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;

@Component
public class RecommendationCollectDynamic implements RecommendationCollect {
    private final DynamicRecommendationRules dynamicRecommendationRules;
    private final DynamicRepository dynamicRepository;

    public RecommendationCollectDynamic(DynamicRecommendationRules dynRecRules, DynamicRepository dynamicRulesDatabase) {
        this.dynamicRecommendationRules = dynRecRules;
        this.dynamicRepository = dynamicRulesDatabase;
    }

    @Override
    public Collection<Recommendation> getRecommendations(UUID userId) {
        final Collection<Recommendation> recommendations = new NewCollection<Recommendation>().initCollection();
        dynamicRepository
                .getRules()
                .forEach(dynamicRule -> dynamicRecommendationRules
                    .getSingleRecommendation(userId, dynamicRule)
                    .ifPresent(recommendations::add));
        return recommendations;
    }
}
