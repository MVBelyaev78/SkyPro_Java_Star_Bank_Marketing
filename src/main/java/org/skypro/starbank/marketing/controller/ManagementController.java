package org.skypro.starbank.marketing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skypro.starbank.marketing.service.ManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
@Tag(name = "Management Controller", description = "Служебный API")
public class ManagementController {
    private final ManagementService managementService;

    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @PostMapping("/clear-caches")
    @Operation(summary = "Очистка всех кэшей",
            description = "Полностью очищает все закэшированные результаты рекомендаций")
    public ResponseEntity<Void> clearAllCaches() {
        managementService.clearAllCaches();
        return ResponseEntity.ok().build();
    }
}
