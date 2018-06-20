package com.hxg.quartz.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 动态修改定时器
 */
@Slf4j
@EnableScheduling
@RestController
public class TaskCronChange implements SchedulingConfigurer {

    private String expression = "0/5 * * * * *";

    @GetMapping("/changeExpression")
    public String changeExpression() {

        expression = "0/1 * * * * *";

        return "changeExpression";
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
//                log.info("configureTasks.run,{}", new Date());
            }
        };

        Trigger trigger = new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                CronTrigger cronTrigger = new CronTrigger(expression);

                return cronTrigger.nextExecutionTime(triggerContext);
            }
        };

        scheduledTaskRegistrar.addTriggerTask(task, trigger);
    }
}
