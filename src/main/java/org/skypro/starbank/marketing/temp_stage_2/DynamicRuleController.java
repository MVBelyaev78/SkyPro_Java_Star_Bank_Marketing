package org.skypro.starbank.marketing.temp_stage_2;

import org.springframework.web.bind.annotation.*;

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
}
