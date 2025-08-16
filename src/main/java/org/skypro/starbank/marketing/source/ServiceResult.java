package org.skypro.starbank.marketing.source;

import java.util.Collection;

public class ServiceResult {
    private final Collection<Recommendation> recommendations;

    public ServiceResult(Collection<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public Collection<Recommendation> getRecommendations() {
        return recommendations;
    }
}
