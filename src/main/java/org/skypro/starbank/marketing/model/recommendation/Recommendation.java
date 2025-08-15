package org.skypro.starbank.marketing.model.recommendation;

import java.util.UUID;

public final class Recommendation {
    private final UUID recommendationId;
    private final String recommendationName;
    private final String recommendationContent;

    public Recommendation(UUID id, String recommendationName, String recommendationContent) {
        this.recommendationId = id;
        this.recommendationName = recommendationName;
        this.recommendationContent = recommendationContent;
    }

    public UUID getRecommendationId() {
        return recommendationId;
    }

    public String getRecommendationName() {
        return recommendationName;
    }

    public String getRecommendationContent() {
        return recommendationContent;
    }

    @Override
    public String toString() {
        return String.format("Product: ID=%s, type=\"%s\", name=\"%s\"",
                recommendationId, recommendationName, recommendationContent);
    }
}
