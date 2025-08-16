package org.skypro.starbank.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public final class Recommendation {
    private final String name;
    private final UUID productId;
    private final String text;

    public Recommendation(String name, UUID productId, String text) {
        this.name = name;
        this.productId = productId;
        this.text = text;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("id")
    public UUID getProductId() {
        return productId;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }
}
