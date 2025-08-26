package org.skypro.starbank.marketing.component.dynamicrule;

import jakarta.annotation.PostConstruct;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DynamicRecommendationSearchMethodFactory {
    private final List<DynRecRuleSearchMethod> dynRecRuleSearchMethods;

    private final Map<String, DynRecRuleSearchMethod> serviceCache = new HashMap<>();

    public DynamicRecommendationSearchMethodFactory(List<DynRecRuleSearchMethod> dynRecRuleSearchMethods) {
        this.dynRecRuleSearchMethods = dynRecRuleSearchMethods;
    }

    @PostConstruct
    private void initServiceCache() {
        dynRecRuleSearchMethods
                .forEach(searchMethod -> serviceCache.put(searchMethod.getQuery(), searchMethod));
    }

    public SearchResult getSearchMethod(String query, String userId, List<String> arguments, Boolean negate) {
        return serviceCache.get(query).getSearchMethod(userId, arguments, negate);
    }
}
