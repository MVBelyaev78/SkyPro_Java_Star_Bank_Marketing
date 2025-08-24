package org.skypro.starbank.marketing.component.dynamicrule;

import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.skypro.starbank.marketing.repository.DynamicRulesRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchMethodFactory {
    private final DynamicRulesRepository dynamicRulesRepository;

    public SearchMethodFactory(DynamicRulesRepository dynamicRulesRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
    }

    public DynamicRulesRepository getDynamicRulesRepository() {
        return dynamicRulesRepository;
    }

    public SearchResult getSearchMethod(String query, String userId, List<String> arguments, Boolean negate) {
        SearchResult searchResult;
        if (query.equals("USER_OF")) {
            searchResult = dynamicRulesRepository.getUserOfQuery(userId, arguments, negate);
        } else if (query.equals("ACTIVE_USER_OF")) {
            searchResult = dynamicRulesRepository.getActiveUserOfQuery(userId, arguments, negate);
        } else {
            searchResult = new SearchResult(false);
        }
        return searchResult;
    }
}
