package org.skypro.starbank.marketing.component;

import org.skypro.starbank.marketing.dto.Recommendation;
import org.skypro.starbank.marketing.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationTopSaving implements RecommendationRule{
    private final RecommendationsRepository recommendationsRepository;

    public RecommendationTopSaving(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {
        Optional<Recommendation> recommendation;
        if (recommendationsRepository.getRecommendationTopSaving(userId.toString()).getResult()) {
            recommendation = Optional.of(new Recommendation(
                    "Top Saving",
                    "59efc529-2fff-41af-baff-90ccd7402925",
                    "Откройте свою собственную «Копилку» с нашим банком!"));
        } else {
            recommendation = Optional.empty();
        }
        return recommendation;
    }
}
