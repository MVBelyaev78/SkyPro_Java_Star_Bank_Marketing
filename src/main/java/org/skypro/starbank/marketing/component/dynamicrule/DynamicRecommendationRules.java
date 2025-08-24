package org.skypro.starbank.marketing.component.dynamicrule;

import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.skypro.starbank.marketing.repository.DynamicRulesRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DynamicRecommendationRules {
    private final DynamicRulesRepository dynamicRulesRepository;
    private final SearchMethodFactory searchMethodFactory;

    public DynamicRecommendationRules(DynamicRulesRepository dynamicRulesRepository, SearchMethodFactory searchMethodFactory) {
        this.dynamicRulesRepository = dynamicRulesRepository;
        this.searchMethodFactory = searchMethodFactory;
    }

    public DynamicRulesRepository getDynamicRulesRepository() {
        return dynamicRulesRepository;
    }

    public SearchMethodFactory getSearchMethodFactory() {
        return searchMethodFactory;
    }

    public Optional<Recommendation> getSingleRecommendation(UUID userId, DynamicRule dynamicRule) {
        Optional<Recommendation> recommendation;
        SearchResult searchResult = dynamicRulesRepository.getUserCheckQuery(userId.toString());
        dynamicRule.getRule()
                .stream()
                .map(queryType -> searchMethodFactory.getSearchMethod(
                    queryType.query(),
                    userId.toString(),
                    queryType.arguments(),
                    queryType.negate()))
                .forEach(sr -> searchResult.setResult(searchResult.getResult() && sr.getResult()));
        if (searchResult.getResult()) {
            recommendation = Optional.of(new Recommendation(
                    dynamicRule.getName(),
                    dynamicRule.getRecommendationUuid(),
                    dynamicRule.getText()));
        } else {
            recommendation = Optional.empty();
        }
        return recommendation;
    }
}
