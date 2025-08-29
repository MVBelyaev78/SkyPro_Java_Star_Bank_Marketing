package org.skypro.starbank.marketing.component.dynamicrule;

import jakarta.annotation.PostConstruct;
import org.skypro.starbank.marketing.dto.dynamicrule.QueryType;
import org.skypro.starbank.marketing.dto.dynamicrule.SearchParameters;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DynamicRecommendationSearchMethodFactory {
    private static final Logger log = Logger.getLogger(DynamicRecommendationSearchMethodFactory.class.getName());

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

    public Optional<SearchResult> getSearchMethod(QueryType queryType, UUID userId) {
        Optional<SearchResult> searchResult;
        if (serviceCache.containsKey(queryType.query())) {
            searchResult = Optional.of(
                    serviceCache
                            .get(queryType.query())
                            .getSearchMethod(new SearchParameters(userId.toString(), queryType.arguments(), queryType.negate())));
        } else {
            log.log(Level.WARNING, String.format("query type %s is not supported", queryType.query()));
            searchResult = Optional.empty();
        }
        return searchResult;
    }
}
