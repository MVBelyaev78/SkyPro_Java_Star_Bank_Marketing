package org.skypro.starbank.marketing.dto.recommendation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record Recommendation(@Schema(description = "Название финансового продукта",
        example = "Вклад 'Копилка'") String name, @Schema(description = "Уникальный идентификатор рекомендации",
        example = "59efc529-2fff-41af-baff-90ccd7402925") UUID uuid,
                             @Schema(description = "Детальное описание предложения и его условий",
                                     example = "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели.") String text) {
    public Recommendation(String name, UUID uuid, String text) {
        this.name = name;
        this.uuid = uuid;
        this.text = text;
    }

    @Override
    @JsonProperty("name")
    public String name() {
        return name;
    }

    @Override
    @JsonProperty("id")
    public UUID uuid() {
        return uuid;
    }

    @Override
    @JsonProperty("text")
    public String text() {
        return text;
    }
}
