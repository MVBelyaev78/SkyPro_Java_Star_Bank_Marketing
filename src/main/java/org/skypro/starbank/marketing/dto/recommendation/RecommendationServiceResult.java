package org.skypro.starbank.marketing.dto.recommendation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collection;
import java.util.UUID;

@Schema(description = "Результат запроса рекомендаций для пользователя")
public record RecommendationServiceResult(UUID userId, Collection<Recommendation> recommendations) {

    @Override
    @JsonProperty("user_id")
    @Schema(description = "UUID пользователя", example = "123e4567-e89b-12d3-a456-426614174000")
    public UUID userId() {
        return userId;
    }

    @Override
    @JsonProperty("recommendations")
    @Schema(description = "Список рекомендаций для пользователя")
    public Collection<Recommendation> recommendations() {
        return recommendations;
    }
}
