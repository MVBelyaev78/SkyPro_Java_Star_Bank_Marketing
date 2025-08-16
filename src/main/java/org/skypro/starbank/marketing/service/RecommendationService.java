package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.source.Recommendation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class RecommendationService {
    public Collection<Recommendation> getServiceResult(String userId) {
        final UUID uuid = UUID.fromString(userId);
        final Collection<Recommendation> serviceResult = new ArrayList<>();
        serviceResult.add(new Recommendation("Yesterday",
                "I said something wrong, now I long for yesterday"));
        serviceResult.add(new Recommendation("Thank you for the music",
                "She says I began to sing long before I could talk"));
        return serviceResult;
    }
}
