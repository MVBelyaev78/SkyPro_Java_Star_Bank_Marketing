package org.skypro.starbank.marketing.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.skypro.starbank.marketing.dto.Recommendation;

import java.util.Collection;
import java.util.UUID;

public class ServiceResult {
    private final UUID userId;
    private final Collection<Recommendation> recommendations;

    public ServiceResult(UUID userId, Collection<Recommendation> recommendations) {
        this.recommendations = recommendations;
        this.userId = userId;
    }

    @JsonProperty("user_id")
    public UUID getUserId() {
        return userId;
    }

    @JsonProperty("recommendations")
    public Collection<Recommendation> getRecommendations() {
        return recommendations;
    }
}
