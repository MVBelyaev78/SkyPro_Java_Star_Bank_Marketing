package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.component.RecommendationRule;
import org.skypro.starbank.marketing.dto.Recommendation;
import org.skypro.starbank.marketing.dto.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {
    private final List<RecommendationRule> rules;

    @Autowired
    public RecommendationService(List<RecommendationRule> rules) {
        this.rules = rules;
    }

    public ServiceResult getServiceResult(String userId) {
        final Collection<Recommendation> recommendations = new ArrayList<>();
        rules.forEach(rule -> rule.getRecommendation(UUID.fromString(userId))
                .ifPresent(recommendations::add));
        return new ServiceResult(UUID.fromString(userId), recommendations);
    }
}
