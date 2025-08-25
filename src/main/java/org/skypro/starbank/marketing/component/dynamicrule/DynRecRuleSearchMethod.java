package org.skypro.starbank.marketing.component.dynamicrule;

import org.skypro.starbank.marketing.dto.recommendation.SearchResult;

import java.util.List;

public interface DynRecRuleSearchMethod {
    String getQuery();
    SearchResult getSearchMethod(String userId, List<String> arguments, Boolean negate);
}
