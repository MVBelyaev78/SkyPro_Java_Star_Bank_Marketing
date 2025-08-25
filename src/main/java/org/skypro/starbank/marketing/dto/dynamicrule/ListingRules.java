package org.skypro.starbank.marketing.dto.dynamicrule;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collection;

@Schema(description = "Список всех динамических правил")
public record ListingRules(Collection<DynamicRule> rules) {

    @Override
    @JsonProperty("data")
    @Schema(description = "Массив правил рекомендаций")
    public Collection<DynamicRule> rules() {
        return rules;
    }
}
