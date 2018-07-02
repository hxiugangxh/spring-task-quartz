package com.hxg.quartz.demo;

import com.hxg.quartz.demo.quartz.HelloJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws Exception {
        /**
         * 1、获取到Scheduler实例，并且启动任务调度器
         * 2、创建具体的任务jboDetail
         * 3、创建出发时间点trigger
         * 4、将jboDetail喝trigger交由scheduler安排触发
         * 5、水淼20秒，关闭定时任务调度器
         */


        SpringApplication.run(DemoApplication.class, args);

        // 步骤一
        log.info("scheduler.start");
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        log.info("instanceName = " + scheduler.getSchedulerName());

        // 步骤二
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("jbo1", "group1").build();

        // 步骤三
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
                .repeatForever();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
                .withSchedule(simpleScheduleBuilder).build();

        scheduler.scheduleJob(jobDetail, trigger);

        TimeUnit.SECONDS.sleep(20);

        scheduler.shutdown();

        log.info(" scheduler.shutdown");

    }
}
