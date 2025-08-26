package org.skypro.starbank.marketing.dto.dynamicrule;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collection;
import java.util.UUID;

@Schema(description = "Динамическое правило для генерации рекомендаций")
public record DynamicRule(
        @Schema(description = "Название финансового продукта", example = "Ипотека 'Молодая семья'", requiredMode = Schema.RequiredMode.REQUIRED) String name,
        @Schema(description = "UUID рекомендуемого продукта", example = "f56b9e7e-eb9a-4f27-9c3a-910a2bb8f7f1", requiredMode = Schema.RequiredMode.REQUIRED) UUID recommendationUuid,
        @Schema(description = "Описание продукта", example = "Специальная программа ипотечного кредитования для молодых семей.") String text,
        @Schema(description = "Список условий правила", requiredMode = Schema.RequiredMode.REQUIRED) Collection<QueryType> rule) {
    public DynamicRule(String name, UUID recommendationUuid, String text, Collection<QueryType> rule) {
        this.name = name;
        this.recommendationUuid = recommendationUuid;
        this.text = text;
        this.rule = rule;
    }

    @JsonProperty("product_name")
    public String name() {
        return name;
    }

    @JsonProperty("product_id")
    public UUID recommendationUuid() {
        return recommendationUuid;
    }

    @JsonProperty("product_text")
    public String getText() {
        return text;
    }

    @JsonProperty("rule")
    public Collection<QueryType> rule() {
        return rule;
    }
}
