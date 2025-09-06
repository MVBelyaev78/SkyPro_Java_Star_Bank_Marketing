package org.skypro.starbank.marketing.component.dynamicrule.common;

import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.repository.DynamicRulesRepository;
import org.skypro.starbank.marketing.repository.DynamicStatisticRepositoryImpl;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DynamicRecommendationRules {
    private final DynamicRulesRepository dynamicRulesRepo;
    private final DynamicStatisticRepositoryImpl dynamicStatisticRepo;
    private final DynamicRecommendationSearchMethodFactory searchMethodFactory;

    public DynamicRecommendationRules(DynamicRulesRepository dynamicRulesRepo,
                                      DynamicStatisticRepositoryImpl dynamicStatisticRepo,
                                      DynamicRecommendationSearchMethodFactory searchMethodFactory) {
        this.dynamicRulesRepo = dynamicRulesRepo;
        this.dynamicStatisticRepo = dynamicStatisticRepo;
        this.searchMethodFactory = searchMethodFactory;
    }

    public Optional<Recommendation> performDynamicRule(UUID userId, DynamicRule dynamicRule) {
        if (!dynamicRulesRepo.getUserCheckQuery(userId.toString()).result()) {
            return Optional.empty();
        }
        Set<Boolean> total = new HashSet<>();
        dynamicRule.rule()
                .forEach(queryType -> searchMethodFactory
                        .getSearchMethod(queryType, userId)
                        .ifPresent(searchResult -> total.add(searchResult.result())));
        if (total.isEmpty() || total.contains(false)) {
            return Optional.empty();
        }
        dynamicStatisticRepo.addRuleStat(dynamicRule.id());
        return Optional.of(new Recommendation(
                dynamicRule.productName(),
                dynamicRule.productId(),
                dynamicRule.productText()));
    }
}
