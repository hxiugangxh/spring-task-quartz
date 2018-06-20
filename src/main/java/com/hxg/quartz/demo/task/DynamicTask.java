package com.hxg.quartz.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@EnableScheduling
@RestController
public class DynamicTask {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> scheduledFuture;

    /**
     * 定义一个方法：startTask-启动任务；
     * 定义一个方法：stopTask-停止定时任务
     * 定义一个方法：changeCron-修改定时任务时间
     */
    @GetMapping("/startTask")
    public String startTask() {

        scheduledFuture = threadPoolTaskScheduler
                .schedule(new MyRunable(), new CronTrigger("0/5 * * * * *"));

        log.info("startTask");

        return "startTask";
    }

    @GetMapping("/stopTask")
    public String stopTask() {

        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }

        return "stopTask";
    }

    @GetMapping("/changeCron")
    public String changeCron() {
        stopTask();
        scheduledFuture = threadPoolTaskScheduler
                .schedule(new MyRunable(), new CronTrigger("0/1 * * * * *"));

        return "changeCron";
    }


    private class MyRunable implements Runnable {
        @Override
        public void run() {
            log.info("MyRunable.run,{}", new Date());
        }
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
}
