package org.skypro.starbank.marketing.repository;

import org.skypro.starbank.marketing.dto.dynamicrule.RuleStatInfoAll;

import java.util.UUID;

public interface DynamicStatisticRepository {
    void addRuleStat(UUID dynamicRuleUuid);

    RuleStatInfoAll getRulesStat();
}
