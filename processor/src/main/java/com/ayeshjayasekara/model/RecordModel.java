package com.ayeshjayasekara.model;

import lombok.Data;

import java.util.Map;

@Data
public class RecordModel {

    private String time;
    private String cpuLoadLast1Minute;
    private String cpuLoadLast5Minute;
    private String cpuLoadLast15Minute;
    private String totalMemory;
    private String usedMemory;
    private String freeMemory;
    private String availableMemory;
    private String usedSwapMemory;
    private String totalSwapMemory;
    private String freeSwapMemory;

    public RecordModel(Map<String, String> memoryMap, Map<String, String> swapMap, Map<String, String> loadMap){
        this.time = loadMap.getOrDefault("time", "0");
        this.cpuLoadLast1Minute = loadMap.getOrDefault("1_minute", "0");
        this.cpuLoadLast5Minute = loadMap.getOrDefault("5_minute", "0");
        this.cpuLoadLast15Minute = loadMap.getOrDefault("15_minute", "0");

        this.totalMemory = memoryMap.getOrDefault("total_memory", "0");
        this.usedMemory = memoryMap.getOrDefault("used_memory", "0");
        this.freeMemory = memoryMap.getOrDefault("free_memory", "0");
        this.availableMemory = memoryMap.getOrDefault("available_memory", "0");

        this.usedSwapMemory = swapMap.getOrDefault("used_swap_memory", "0");
        this.totalSwapMemory = memoryMap.getOrDefault("total_swap_memory", "0");
        this.freeSwapMemory = memoryMap.getOrDefault("free_swap_memory", "0");
    }
}
