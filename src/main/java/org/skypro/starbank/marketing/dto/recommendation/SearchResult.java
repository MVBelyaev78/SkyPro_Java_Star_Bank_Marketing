package org.skypro.starbank.marketing.dto.recommendation;

public final class SearchResult {
    private Boolean result;

    public SearchResult(Boolean result) {
        this.result = result;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
