package org.skypro.starbank.marketing.dynamicrule.controller;

import org.skypro.starbank.marketing.dynamicrule.dto.DynamicRule;
import org.skypro.starbank.marketing.dynamicrule.service.DynamicRuleService;
import org.skypro.starbank.marketing.dynamicrule.dto.ListingRules;
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
