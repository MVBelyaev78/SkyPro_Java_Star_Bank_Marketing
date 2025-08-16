package org.skypro.starbank.marketing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponse {
    private UUID userId;
    private List<RecommendationProduct> recommendations;
}
