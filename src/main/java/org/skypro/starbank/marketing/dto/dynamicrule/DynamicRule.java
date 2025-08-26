package org.skypro.starbank.marketing.dto.dynamicrule;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collection;
import java.util.UUID;

@Schema(description = "Динамическое правило для генерации рекомендаций")
public record DynamicRule(UUID uuid,
                          @Schema(description = "Название финансового продукта", example = "Ипотека 'Молодая семья'", requiredMode = Schema.RequiredMode.REQUIRED) String name,
                          @Schema(description = "UUID рекомендуемого продукта", example = "f56b9e7e-eb9a-4f27-9c3a-910a2bb8f7f1", requiredMode = Schema.RequiredMode.REQUIRED) UUID recommendationUuid,
                          @Schema(description = "Описание продукта", example = "Специальная программа ипотечного кредитования для молодых семей.") String text,
                          @Schema(description = "Список условий правила", requiredMode = Schema.RequiredMode.REQUIRED) Collection<QueryType> rule) {
    public DynamicRule(UUID uuid, String name, UUID recommendationUuid, String text, Collection<QueryType> rule) {
        this.uuid = uuid;
        this.name = name;
        this.recommendationUuid = recommendationUuid;
        this.text = text;
        this.rule = rule;
    }

    @Override
    @JsonProperty("id")
    @Schema(description = "Уникальный идентификатор правила", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890", accessMode = Schema.AccessMode.READ_ONLY)
    public UUID uuid() {
        return uuid;
    }

    @Override
    @JsonProperty("product_name")
    public String name() {
        return name;
    }

    @Override
    @JsonProperty("product_id")
    public UUID recommendationUuid() {
        return recommendationUuid;
    }

    @Override
    @JsonProperty("product_text")
    public String text() {
        return text;
    }

    @Override
    @JsonProperty("rule")
    public Collection<QueryType> rule() {
        return rule;
    }
}
