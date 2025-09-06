package org.skypro.starbank.marketing.dto.dynamicrule;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collection;

@Schema(description = "Сводные данные срабатываний всех динамических правил")
public record RuleStatInfoAll(
        @Schema(description = "Коллекция данных срабатываний динамических правил")
        @JsonProperty("stats")
        Collection<RuleStatInfo> statInfoAll
) {
}
