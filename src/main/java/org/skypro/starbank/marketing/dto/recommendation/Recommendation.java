package org.skypro.starbank.marketing.dto.recommendation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public final class Recommendation {
    @Schema(description = "Название финансового продукта",
            example = "Вклад 'Копилка'")
    private final String name;
    @Schema(description = "Уникальный идентификатор рекомендации",
            example = "59efc529-2fff-41af-baff-90ccd7402925")
    private final UUID uuid;
    @Schema(description = "Детальное описание предложения и его условий",
            example = "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели.")
    private final String text;

    public Recommendation(String name, UUID uuid, String text) {
        this.name = name;
        this.uuid = uuid;
        this.text = text;
    }

    public Recommendation(String name, String id, String text) {
        this(name, UUID.fromString(id), text);
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("id")
    public UUID getUuid() {
        return uuid;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }
}
