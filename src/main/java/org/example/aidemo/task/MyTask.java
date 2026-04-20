package org.example.aidemo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MyTask {
    public void execute(){
        log.info("定时任务开始执行...", new Date());
    }
}
