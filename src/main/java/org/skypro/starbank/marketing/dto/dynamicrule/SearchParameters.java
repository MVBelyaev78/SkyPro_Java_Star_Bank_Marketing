package org.skypro.starbank.marketing.dto.dynamicrule;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Параметры поиска рекомендаций согласно динамическим правилам")
public record SearchParameters(
        @Schema(description = "UUID пользователя", example = "ff5813cb-3cc2-45cc-a997-daf264c6000c")
        String userId,

        @Schema(description = "Аргументы для запроса", example = "[\"DEBIT\", \"CREDIT\"]")
        List<String> arguments,

        @Schema(description = "Флаг инверсии условия", example = "false")
        Boolean negate) {
}
