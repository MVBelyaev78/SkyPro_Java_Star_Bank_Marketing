package org.skypro.starbank.marketing.dynamicrule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.UUID;

public class DynamicRule {
    private UUID uuid;
    private final String name;
    private final UUID recommendationUuid;
    private final String text;
    private final Collection<QueryType> rule;

    public DynamicRule(UUID uuid, String name, UUID recommendationUuid, String text, Collection<QueryType> rule) {
        this.uuid = uuid;
        this.name = name;
        this.recommendationUuid = recommendationUuid;
        this.text = text;
        this.rule = rule;
    }

    @JsonProperty("id")
    public UUID getUuid() {
        return uuid;
    }

    @JsonProperty("id")
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("product_name")
    public String getName() {
        return name;
    }

    @JsonProperty("product_id")
    public UUID getRecommendationUuid() {
        return recommendationUuid;
    }

    @JsonProperty("product_text")
    public String getText() {
        return text;
    }

    @JsonProperty("rule")
    public Collection<QueryType> getRule() {
        return rule;
    }
}
