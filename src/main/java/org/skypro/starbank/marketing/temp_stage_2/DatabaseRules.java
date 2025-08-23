package org.skypro.starbank.marketing.temp_stage_2;

import java.util.UUID;

public interface DatabaseRules {
    DynamicRule addRules(DynamicRule rule);
    ListingRules getListingRules();
    void deleteRule(UUID recommendationUuid);
}
