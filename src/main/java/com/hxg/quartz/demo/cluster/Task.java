package com.hxg.quartz.demo.cluster;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Slf4j
@Configuration
@EnableScheduling
public class Task {

    @Scheduled(cron = "0/15 * * * * ?")
    @SchedulerLock(name = "scheduledTaskName", lockAtMostFor = 9000, lockAtLeastFor = 9000)
    public void processItem() throws InterruptedException {
        log.warn("@Scheduled service, date = {}", new Date());
    }
}
