package org.example.aidemo.common.schedule;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

public class Schedule111 {
    @Scheduled(cron = "0 30 23 10 4 ?")
    public void exectueTimer(){
        System.out.println("定时任务：当前系统时间"+ LocalDateTime.now());
    }
}
