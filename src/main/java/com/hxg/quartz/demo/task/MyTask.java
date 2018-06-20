package com.hxg.quartz.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * spring boot 定时器task用法
 */
@Slf4j
@Configuration
@EnableScheduling
public class MyTask {

    @Scheduled(cron = "0/10 * * * * *")
    public void task1() {
//        log.info("myTask.task1--{}", new Date());
    }

    @Scheduled(cron = "10 0/1 * * * *")
    public void task2() {
//        log.info("myTask.task2--{}", new Date());
    }

}
