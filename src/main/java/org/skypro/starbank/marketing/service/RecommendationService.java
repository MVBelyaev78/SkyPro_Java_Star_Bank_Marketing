package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.model.recommendation.Recommendation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class RecommendationService {
    public Collection<Recommendation> getRecommendationList(String userId) {
        Collection<Recommendation> recommendations = new ArrayList<>();
        recommendations.add(new Recommendation(UUID.randomUUID(), "Yesterday",
                "I said something wrong, now I long for yesterday"));
        recommendations.add(new Recommendation(UUID.randomUUID(), "Thank you for the music",
                "She says I began to sing long before I could talk"));
        return recommendations;
    }
}
