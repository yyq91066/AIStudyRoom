package org.example.aidemo.common.schedule;

import java.time.LocalDateTime;
public interface ScheduleTest {
    //java8新特性，接口默认方法
    default void exectueTimer(){
        System.out.println("定时任务：当前系统时间"+ LocalDateTime.now());
    }
}
