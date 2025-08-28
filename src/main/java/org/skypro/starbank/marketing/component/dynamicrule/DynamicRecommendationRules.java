package org.skypro.starbank.marketing.component.dynamicrule;

import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.repository.DynamicRulesRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DynamicRecommendationRules {
    private final DynamicRulesRepository dynamicRulesRepository;
    private final DynamicRecommendationSearchMethodFactory searchMethodFactory;

    public DynamicRecommendationRules(DynamicRulesRepository dynamicRulesRepository, DynamicRecommendationSearchMethodFactory searchMethodFactory) {
        this.dynamicRulesRepository = dynamicRulesRepository;
        this.searchMethodFactory = searchMethodFactory;
    }

    public Optional<Recommendation> getSingleRecommendation(UUID userId, DynamicRule dynamicRule) {
        if (!dynamicRulesRepository.getUserCheckQuery(userId.toString()).getResult()) {
            return Optional.empty();
        }
        Set<Boolean> total = new HashSet<>();
        dynamicRule.rule()
                .forEach(queryType -> searchMethodFactory
                        .getSearchMethod(queryType, userId)
                        .ifPresent(searchResult -> total.add(searchResult.getResult())));
        if (total.isEmpty() || total.contains(false)) {
            return Optional.empty();
        }
        return Optional.of(new Recommendation(
                dynamicRule.name(),
                dynamicRule.recommendationUuid(),
                dynamicRule.text()));
    }
}
