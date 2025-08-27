package org.skypro.starbank.marketing.dto.recommendation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Результат проверки условия")
public final class SearchResult {

    @Schema(description = "Результат проверки условия", example = "true")
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
