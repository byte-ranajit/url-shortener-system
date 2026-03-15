package com.url_service.util;

import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public synchronized long nextId(){
        long timestamp = System.currentTimeMillis();
        if (timestamp == lastTimestamp){
            sequence = (sequence + 1) & 4095;
            if (sequence == 0){
                while (timestamp <= lastTimestamp){
                    timestamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        long epoch = 1700000000000L;
        long machineId = 1L;
        return Math.abs(
                ((timestamp - epoch) << 22)
                        | (machineId << 12)
                        | sequence
        );
    }
}
