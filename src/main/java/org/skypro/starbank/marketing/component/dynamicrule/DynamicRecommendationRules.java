package org.skypro.starbank.marketing.component.dynamicrule;

import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.skypro.starbank.marketing.repository.DynamicRulesRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DynamicRecommendationRules {
    private final DynamicRulesRepository dynamicRulesRepository;
    private final DynamicRecommendationSearchMethodFactory searchMethodFactory;

    public DynamicRecommendationRules(DynamicRulesRepository dynamicRulesRepository, DynamicRecommendationSearchMethodFactory searchMethodFactory) {
        this.dynamicRulesRepository = dynamicRulesRepository;
        this.searchMethodFactory = searchMethodFactory;
    }

    public Optional<Recommendation> getSingleRecommendation(UUID userId, DynamicRule dynamicRule) {
        Optional<Recommendation> recommendation;
        SearchResult searchResult = dynamicRulesRepository.getUserCheckQuery(userId.toString());
        dynamicRule.rule()
                .stream()
                .map(queryType -> searchMethodFactory.getSearchMethod(
                        queryType.query(),
                        userId.toString(),
                        queryType.arguments(),
                        queryType.negate()))
                .forEach(sr -> searchResult.setResult(searchResult.getResult() && sr.getResult()));
        if (searchResult.getResult()) {
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
