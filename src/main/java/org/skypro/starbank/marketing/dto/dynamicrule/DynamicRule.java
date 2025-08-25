package org.skypro.starbank.marketing.dto.dynamicrule;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collection;
import java.util.UUID;

@Schema(description = "Динамическое правило для генерации рекомендаций")
public class DynamicRule {
    private UUID uuid;

    @Schema(description = "Название финансового продукта", example = "Ипотека 'Молодая семья'", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String name;

    @Schema(description = "UUID рекомендуемого продукта", example = "f56b9e7e-eb9a-4f27-9c3a-910a2bb8f7f1", requiredMode = Schema.RequiredMode.REQUIRED)
    private final UUID recommendationUuid;

    @Schema(description = "Описание продукта", example = "Специальная программа ипотечного кредитования для молодых семей.")
    private final String text;

    @Schema(description = "Список условий правила", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Collection<QueryType> rule;

    public DynamicRule(UUID uuid, String name, UUID recommendationUuid, String text, Collection<QueryType> rule) {
        this.uuid = uuid;
        this.name = name;
        this.recommendationUuid = recommendationUuid;
        this.text = text;
        this.rule = rule;
    }

    @JsonProperty("id")
    @Schema(description = "Уникальный идентификатор правила", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890", accessMode = Schema.AccessMode.READ_ONLY)
    public UUID getUuid() {
        return uuid;
    }

    @JsonProperty("id")
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("product_name")
    public String getName() {
        return name;
    }

    @JsonProperty("product_id")
    public UUID getRecommendationUuid() {
        return recommendationUuid;
    }

    @JsonProperty("product_text")
    public String getText() {
        return text;
    }

    @JsonProperty("rule")
    public Collection<QueryType> getRule() {
        return rule;
    }
}
