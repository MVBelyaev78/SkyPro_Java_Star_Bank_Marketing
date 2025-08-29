package org.skypro.starbank.marketing.service;

import io.swagger.v3.oas.annotations.Operation;
import org.skypro.starbank.marketing.dto.dynamicrule.RuleStatInfoAll;
import org.skypro.starbank.marketing.repository.DynamicRepository;
import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.dynamicrule.ListingRules;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DynamicRuleService {
    private final DynamicRepository repo;

    public DynamicRuleService(DynamicRepository repo) {
        this.repo = repo;
    }

    @Operation(summary = "Добавить правило",
            description = "Создает и сохраняет новое динамическое правило рекомендаций")
    public DynamicRule addRules(DynamicRule rule) {
        return repo.addRule(rule);
    }

    @Operation(summary = "Получить все правила",
            description = "Возвращает все активные динамические правила рекомендаций")
    public ListingRules getListingRules() {
        return new ListingRules(repo.getRules());
    }

    @Operation(summary = "Удалить правило",
            description = "Удаляет динамическое правило по UUID рекомендации")
    public void deleteRule(UUID recommendationUuid) {
        repo.deleteRule(recommendationUuid);
    }

    @Operation(summary = "Получить статистику срабатываний правил",
            description = "Возвращает статистику срабатываний динамических правил")
    public RuleStatInfoAll getRulesStat() {
        return repo.getRulesStat();
    }
}
