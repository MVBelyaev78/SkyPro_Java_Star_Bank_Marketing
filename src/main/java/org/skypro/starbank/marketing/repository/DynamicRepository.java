package org.skypro.starbank.marketing.repository;

import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.dynamicrule.RuleStatInfoAll;

import java.util.Collection;
import java.util.UUID;

public interface DynamicRepository {
    DynamicRule addRule(DynamicRule rule);

    Collection<DynamicRule> getRules();

    void deleteRule(UUID recommendationUuid);
}
