package org.skypro.starbank.marketing.dto.dynamicrule;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Данные статистики срабатывания динамического правила")
public record RuleStatInfo(
        @Schema(description = "UUID динамического правила",
                example = "f56b9e7e-eb9a-4f27-9c3a-910a2bb8f7f1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonProperty("rule_id")
        UUID recommendationUuid,

        @Schema(description = "Количество срабатываний динамического правила",
                example = "100",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonProperty("count")
        Integer count
) {
}
