package org.skypro.starbank.marketing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skypro.starbank.marketing.service.RecommendationService;
import org.skypro.starbank.marketing.result.ServiceResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Tag(name = "Recommendation API", description = "Операции для получения персональных рекомендаций")
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    @Operation(
            summary = "Получить список рекомендаций для пользователя",
            description = "Возвращает персонализированный список продуктов банка, подобранный на основе ID пользователя"
    )
    @ApiResponse(responseCode = "200", description = "Успешный запрос, рекомендации найдены")
    @ApiResponse(responseCode = "404", description = "Пользователь с указанным ID не найден")
    public ServiceResult getRecommendationList(
            @Parameter(
            description = "Уникальный идентификатор пользователя в системе банка",
            required = true,
            example = "f37ba8a8-3cd5-4976-9f74-2b21f105da67"
    )
            @PathVariable String userId) {
        return recommendationService.getServiceResult(UUID.fromString(userId));
    }
}
