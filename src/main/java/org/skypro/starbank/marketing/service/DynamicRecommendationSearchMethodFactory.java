package org.skypro.starbank.marketing.service;

import jakarta.annotation.PostConstruct;
import org.skypro.starbank.marketing.component.dynamicrule.DynRecRuleSearchMethod;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamicRecommendationSearchMethodFactory {
    @Autowired
    private List<DynRecRuleSearchMethod> dynRecRuleSearchMethods;

    private final Map<String, DynRecRuleSearchMethod> serviceCache = new HashMap<>();

    @PostConstruct
    private void initServiceCache() {
        dynRecRuleSearchMethods
                .forEach(searchMethod -> serviceCache.put(searchMethod.getQuery(), searchMethod));
    }

    public SearchResult getSearchMethod(String query, String userId, List<String> arguments, Boolean negate) {
        return serviceCache.get(query).getSearchMethod(userId, arguments, negate);
    }
}
