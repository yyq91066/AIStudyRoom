package org.example.aidemo.common.schedule;

import org.example.aidemo.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTaskOne {
    @Autowired
    private ScheduleTestImpl scheuleTestImpl;
    @Autowired
    private EmailService emailService;


//    @Scheduled(cron = "${MyParameter.Schedule.corn}")
//    @Scheduled(fixedDelay = 5000)
//    @Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 0 23 * * *")
//    @Scheduled(cron = "0/5 * * * * ?")

    public void exectueTimer() {
        scheuleTestImpl.exectueTimer();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//      emailService.sendSimpleEmail("208241594@qq.com","定时任务", "时间："+LocalDateTime.now());

    }
}
