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

    public DynamicRecommendationRules(DynamicRulesRepository dynamicRulesRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
    }

    public DynamicRulesRepository getDynamicRulesRepository() {
        return dynamicRulesRepository;
    }

    public Optional<Recommendation> getSingleRecommendation(UUID userId, DynamicRule dynamicRule) {
        Optional<Recommendation> recommendation;
        SearchResult searchResult = dynamicRulesRepository.getUserCheckQuery(userId.toString());
        dynamicRule.getRule()
                .stream()
                .map(queryType -> getComponentMethod(
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

    protected SearchResult getComponentMethod(String query, String userId, List<String> arguments, Boolean negate) {
        SearchResult searchResult;
        if (query.equals("USER_OF")) {
            searchResult = getDynamicRulesRepository().getUserOfQuery(userId, arguments, negate);
        } else if (query.equals("ACTIVE_USER_OF")) {
            searchResult = getDynamicRulesRepository().getActiveUserOfQuery(userId, arguments, negate);
        } else {
            searchResult = new SearchResult(false);
        }
        return searchResult;
    }
}
