package org.skypro.starbank.marketing.ruleset;

import org.skypro.starbank.marketing.model.recommendation.Recommendation;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TopSavingRuleSet implements RecommendationRuleSet{
    @Override
    public Optional<Recommendation> getRecommendation(UUID userID) {
        return Optional.empty();
    }
}
