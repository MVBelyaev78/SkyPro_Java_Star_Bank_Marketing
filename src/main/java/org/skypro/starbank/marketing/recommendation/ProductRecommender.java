package org.skypro.starbank.marketing.recommendation;

import java.util.List;
import java.util.UUID;

public interface ProductRecommender {
    List<ProductRecommender> getRecommendation(UUID clientId);
}
