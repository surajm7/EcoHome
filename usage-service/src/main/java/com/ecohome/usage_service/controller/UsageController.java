package com.ecohome.usage_service.controller;

import com.ecohome.usage_service.dto.UsageResponseDto;
import com.ecohome.usage_service.service.UsageQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usage")
public class UsageController {

    private final UsageQueryService usageQueryService;

    public UsageController(UsageQueryService usageQueryService) {
        this.usageQueryService = usageQueryService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UsageResponseDto> getUsageForUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "3") int days) {
        return ResponseEntity.ok(usageQueryService.getUsageForUser(userId, days));
    }
}
