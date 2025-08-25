package org.skypro.starbank.marketing.dto.recommendation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.UUID;

public record RecommendationServiceResult(UUID userId, Collection<Recommendation> recommendations) {

    @Override
    @JsonProperty("user_id")
    public UUID userId() {
        return userId;
    }

    @Override
    @JsonProperty("recommendations")
    public Collection<Recommendation> recommendations() {
        return recommendations;
    }
}
