package org.skypro.starbank.marketing.component;

import org.skypro.starbank.marketing.dto.Recommendation;
import org.skypro.starbank.marketing.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class RecommendationRuleSimpleCredit implements RecommendationRule {
    private final RecommendationsRepository recommendationsRepository;

    public RecommendationRuleSimpleCredit(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public RecommendationsRepository getRecommendationsRepository() {
        return recommendationsRepository;
    }

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {

        if (!recommendationsRepository.getSimpleCreditEligibility(userId.toString()).getResult()) {
            return Optional.empty();
        }


        if (recommendationsRepository.hasActiveCredits(userId.toString())) {
            return Optional.empty();
        }


        return Optional.of(new Recommendation(
                "Простой кредит",
                "ab138afb-f3ba-4a93-b74f-0fcee86d447f",
                "Клиент может получить кредит: быстрое рассмотрение заявки, удобное оформление"));
    }
}
