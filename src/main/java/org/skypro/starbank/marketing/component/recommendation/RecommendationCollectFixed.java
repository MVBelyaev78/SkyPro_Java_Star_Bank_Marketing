package org.skypro.starbank.marketing.component.recommendation;

import org.skypro.starbank.marketing.auxiliary.NewCollection;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@Component
public class RecommendationCollectFixed implements RecommendationCollect {
    private final Collection<RecommendationRule> fixedRecRules;

    public RecommendationCollectFixed(Collection<RecommendationRule> fixedRecRules) {
        this.fixedRecRules = fixedRecRules;
    }

    @Override
    public Collection<Recommendation> getRecommendations(UUID userId) {
        final Collection<Recommendation> recommendations = new NewCollection<Recommendation>().initCollection();
        fixedRecRules.forEach(fixedRecRule -> fixedRecRule
                .getRecommendation(userId)
                .ifPresent(recommendations::add));
        return recommendations;
    }
}
