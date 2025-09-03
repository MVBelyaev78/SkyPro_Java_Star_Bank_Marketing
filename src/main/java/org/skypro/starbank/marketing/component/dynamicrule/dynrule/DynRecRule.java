package org.skypro.starbank.marketing.component.dynamicrule.dynrule;

import org.skypro.starbank.marketing.dto.dynamicrule.SearchParameters;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;

public interface DynRecRule {
    String getQuery();
    SearchResult getSearchMethod(SearchParameters searchParameters);
}
