package org.skypro.starbank.marketing.source;

public final class Recommendation {
    private final String name;
    private final String text;

    public Recommendation(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
