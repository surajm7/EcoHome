package com.ecohome.usage_service.dto;

import java.util.List;

public record UsageResponseDto(
        Long userId,
        List<DeviceUsageDto> devices
) {
    public record DeviceUsageDto(
            Long id,
            String name,
            String type,
            String location,
            double energyConsumed
    ) {
    }
}
