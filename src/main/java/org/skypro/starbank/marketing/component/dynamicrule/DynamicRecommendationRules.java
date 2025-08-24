package org.skypro.starbank.marketing.component.dynamicrule;

import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.dynamicrule.QueryType;
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
        if (dynamicRule.getRule().isEmpty()) {
            return Optional.empty();
        }
        SearchResult searchResult = dynamicRulesRepository.getUserCheckQuery(userId.toString());
        if (!searchResult.getResult()) {
            return Optional.empty();
        }
        for (QueryType queryType : dynamicRule.getRule()) {
            final SearchResult sr = getComponentMethod(
                    queryType.query(),
                    userId.toString(),
                    queryType.arguments(),
                    queryType.negate());
            searchResult.setResult(searchResult.getResult() && sr.getResult());
        }
        if (!searchResult.getResult()) {
            return Optional.empty();
        }
        return Optional.of(new Recommendation(
                dynamicRule.getName(),
                dynamicRule.getRecommendationUuid(),
                dynamicRule.getText()));
    }

    protected SearchResult getComponentMethod(String query, String userId, List<String> arguments, Boolean negate) {
        if (query.equals("USER_OF")) {
            return getDynamicRulesRepository().getUserOfQuery(userId, arguments, negate);
        }
        return new SearchResult(true);
    }
}
