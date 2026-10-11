package com.ecohome.usage_service.service;

import com.ecohome.usage_service.client.DeviceClient;
import com.ecohome.usage_service.dto.DeviceDto;
import com.ecohome.usage_service.dto.UsageResponseDto;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsageQueryService {

    private final InfluxDBClient influxDBClient;
    private final DeviceClient deviceClient;

    @Value("${influx.bucket}")
    private String influxBucket;

    @Value("${influx.org}")
    private String influxOrg;

    public UsageQueryService(InfluxDBClient influxDBClient, DeviceClient deviceClient) {
        this.influxDBClient = influxDBClient;
        this.deviceClient = deviceClient;
    }

    public UsageResponseDto getUsageForUser(Long userId, int days) {
        String fluxQuery = String.format("""
                from(bucket: "%s")
                  |> range(start: -%dd)
                  |> filter(fn: (r) => r["_measurement"] == "energy_usage")
                  |> filter(fn: (r) => r["_field"] == "energyConsumed")
                  |> group(columns: ["deviceId"])
                  |> sum(column: "_value")
                """, influxBucket, days);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(fluxQuery, influxOrg);

        List<UsageResponseDto.DeviceUsageDto> devices = new ArrayList<>();

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                Object deviceIdObj = record.getValueByKey("deviceId");
                if (deviceIdObj == null) {
                    continue;
                }
                Long deviceId;
                try {
                    deviceId = Long.valueOf(deviceIdObj.toString());
                } catch (NumberFormatException e) {
                    continue;
                }

                DeviceDto device;
                try {
                    device = deviceClient.getDeviceById(deviceId);
                } catch (Exception e) {
                    continue;
                }
                if (device == null || device.userId() == null || !device.userId().equals(userId)) {
                    continue;
                }

                Object value = record.getValueByKey("_value");
                double energy = value instanceof Number n ? n.doubleValue() : 0.0;

                devices.add(new UsageResponseDto.DeviceUsageDto(
                        device.id(),
                        device.name(),
                        device.type(),
                        device.location(),
                        energy));
            }
        }

        return new UsageResponseDto(userId, devices);
    }
}
