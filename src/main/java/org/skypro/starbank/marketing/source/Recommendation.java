package org.skypro.starbank.marketing.model.recommendation;

import java.util.UUID;

public final class Recommendation {
    private final UUID id;
    private final String name;
    private final String text;

    public Recommendation(UUID id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
