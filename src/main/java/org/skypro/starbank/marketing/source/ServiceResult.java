package org.skypro.starbank.marketing.source;

import java.util.Collection;
import java.util.UUID;

public class ServiceResult {
    private final UUID userId;
    private final Collection<Recommendation> recommendations;

    public ServiceResult(UUID userId, Collection<Recommendation> recommendations) {
        this.userId = userId;
        this.recommendations = recommendations;
    }

    public UUID getUserId() {
        return userId;
    }

    public Collection<Recommendation> getRecommendations() {
        return recommendations;
    }
}
