package org.skypro.starbank.marketing.dto.dynamicrule;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Элемент условия внутри правила рекомендаций")
public record QueryType(
        @Schema(description = "Тип запроса/условия", example = "USER_OF", requiredMode = Schema.RequiredMode.REQUIRED)
        String query,

        @Schema(description = "Аргументы для запроса", example = "[\"DEBIT\", \"CREDIT\"]")
        List<String> arguments,

        @Schema(description = "Флаг инверсии условия", example = "false")
        Boolean negate) {
}
