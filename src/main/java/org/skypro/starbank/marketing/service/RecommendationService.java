package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.component.RecommendationRule;
import org.skypro.starbank.marketing.dto.Recommendation;
import org.skypro.starbank.marketing.result.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {
    private final Collection<RecommendationRule> rules;

    @Autowired
    public RecommendationService(Collection<RecommendationRule> rules) {
        this.rules = rules;
    }

    public ServiceResult getServiceResult(UUID userId) {
        final Collection<Recommendation> recommendations = new ArrayList<>();
        rules.forEach(rule -> rule.getRecommendation(userId)
                .ifPresent(recommendations::add));
        return new ServiceResult(userId, recommendations);
    }
}
