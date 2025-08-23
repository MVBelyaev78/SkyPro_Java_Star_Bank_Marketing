package org.skypro.starbank.marketing.recommendation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public final class Recommendation {
    private final String name;
    private final UUID uuid;
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
