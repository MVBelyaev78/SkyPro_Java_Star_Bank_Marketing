package org.skypro.starbank.marketing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.dynamicrule.ListingRules;
import org.skypro.starbank.marketing.dto.dynamicrule.RuleStatInfoAll;
import org.skypro.starbank.marketing.service.DynamicRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rule")
@Tag(name = "Dynamic Rule Controller", description = "API для управления динамическими правилами рекомендаций")
public class DynamicRuleController {
    private final DynamicRuleService dynamicRuleService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService) {
        this.dynamicRuleService = dynamicRuleService;
    }

    @PostMapping("")
    @Operation(summary = "Добавить новое правило",
            description = "Создает и добавляет новое правило для генерации рекомендаций в систему.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Правило успешно создано",
                    content = @Content(schema = @Schema(implementation = DynamicRule.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса")
    })
    public DynamicRule addRules(@RequestBody DynamicRule rule) {
        return dynamicRuleService.addRules(rule);
    }

    @GetMapping("")
    @Operation(summary = "Получить список всех правил",
            description = "Возвращает полный перечень всех активных правил генерации рекомендаций.")
    @ApiResponse(responseCode = "200", description = "Успешный запрос",
            content = @Content(schema = @Schema(implementation = ListingRules.class)))
    public ListingRules getListingRules() {
        return dynamicRuleService.getListingRules();
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Удалить правило по ID",
            description = "Удаляет правило генерации рекомендаций по его уникальному идентификатору.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Правило успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Правило не найдено")
    })
    public ResponseEntity<Void> deleteRule(@Parameter(description = "UUID правила для удаления", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
                                           @PathVariable String recommendationUuid) {
        try {
            UUID uuid = UUID.fromString(recommendationUuid);
            dynamicRuleService.deleteRule(uuid);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("stats")
    @Operation(summary = "Получить статистику срабатываний правил",
            description = "Возвращает данные по срабатываниям всех динамических правил.")
    @ApiResponse(responseCode = "200", description = "Успешный запрос",
            content = @Content(schema = @Schema(implementation = RuleStatInfoAll.class)))
    public RuleStatInfoAll getRulesStat() {
        return dynamicRuleService.getRulesStat();
    }
}
