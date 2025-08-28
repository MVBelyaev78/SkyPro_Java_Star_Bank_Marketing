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
        Optional<Recommendation> recommendation;
        dynamicRule.rule().forEach(queryType -> searchMethodFactory.getSearchMethod(
                queryType.query(),
                userId.toString(),
                queryType.arguments(),
                queryType.negate()).ifPresent(searchResult -> total.add(searchResult.getResult())));
        if (!total.isEmpty() && !total.contains(false)) {
            recommendation = Optional.of(new Recommendation(
                    dynamicRule.name(),
                    dynamicRule.recommendationUuid(),
                    dynamicRule.text()));
        } else {
            recommendation = Optional.empty();
        }
        return recommendation;
    }
}
