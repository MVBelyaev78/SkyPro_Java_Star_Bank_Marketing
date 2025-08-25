package org.skypro.starbank.marketing.controller;

import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.service.DynamicRuleService;
import org.skypro.starbank.marketing.dto.dynamicrule.ListingRules;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {
    private final DynamicRuleService dynamicRuleService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService) {
        this.dynamicRuleService = dynamicRuleService;
    }

    @PostMapping("")
    public DynamicRule addRules(@RequestBody DynamicRule rule) {
        return dynamicRuleService.addRules(rule);
    }

    @GetMapping("")
    public ListingRules getListingRules() {
        return dynamicRuleService.getListingRules();
    }

    @DeleteMapping("/{recommendationUuid}")
    public void deleteRule(@PathVariable String recommendationUuid) {
        dynamicRuleService.deleteRule(UUID.fromString(recommendationUuid));
    }
}
