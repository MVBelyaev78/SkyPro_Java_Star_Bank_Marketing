package org.skypro.starbank.marketing.dto.recommendation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Результат проверки условия")
public record SearchResult(@Schema(description = "Результат проверки условия", example = "true") Boolean result) {
}
