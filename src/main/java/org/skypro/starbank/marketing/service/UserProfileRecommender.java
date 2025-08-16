package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.recommendation.ProductRecommender;
import org.skypro.starbank.marketing.repository.RecommendationsRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Primary
public class UserProfileRecommender implements ProductRecommender {
    private final RecommendationsRepository recommendationsRepository;


    public UserProfileRecommender(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }


    @Override
    public List<ProductRecommender> getRecommendation(UUID clientId) {
        return Collections.emptyList();
    }
}
