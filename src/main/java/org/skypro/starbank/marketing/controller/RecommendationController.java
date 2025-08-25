package org.skypro.starbank.marketing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skypro.starbank.marketing.service.RecommendationService;
import org.skypro.starbank.marketing.dto.recommendation.RecommendationServiceResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
@Tag(name = "Recommendation Controller", description = "API для получения персонализированных рекомендаций")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Получить рекомендации для пользователя",
            description = "Возвращает персонализированный список финансовых продуктов для указанного пользователя.")
    @ApiResponse(responseCode = "200", description = "Успешный запрос",
            content = @Content(schema = @Schema(implementation = RecommendationServiceResult.class)))
    public RecommendationServiceResult getRecommendationList(@Parameter(description = "UUID пользователя", example = "123e4567-e89b-12d3-a456-426614174000")
                                                                 @PathVariable String userId) {
        return recommendationService.getServiceResult(UUID.fromString(userId));
    }
}
