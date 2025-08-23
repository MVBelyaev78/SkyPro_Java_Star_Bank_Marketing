package org.skypro.starbank.marketing.dynamicrule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public record ListingRules(Collection<DynamicRule> rules) {

    @Override
    @JsonProperty("data")
    public Collection<DynamicRule> rules() {
        return rules;
    }
}
