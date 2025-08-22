package org.skypro.starbank.marketing.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.skypro.starbank.marketing.dto.Recommendation;

import java.util.Collection;
import java.util.UUID;

public class ServiceResult {
    @Schema(description = "UUID пользователя, для которого подобраны рекомендации",
            example = "f37ba8a8-3cd5-4976-9f74-2b21f105da67")
    private final UUID userId;

    @Schema(description = "Список рекомендованных финансовых продуктов")
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
