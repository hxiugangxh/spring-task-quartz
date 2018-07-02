package com.hxg.quartz.demo.quartz.task;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;


public class BaseTask implements Job {

    @Autowired
    @Qualifier("schedulerFactoryBean")
    private Scheduler scheduler;

    @PostConstruct // 等同于 init-method的配置.
    public void init() {

        JobKey jobKey = getJobKey();
        JobDetail jobDetail = JobBuilder.newJob(getJobClass()).withIdentity(jobKey).build();
        String cronExpression = getCronExpression();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobKey.getName(), jobKey.getGroup()).withSchedule
				(CronScheduleBuilder.cronSchedule(cronExpression)).build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    public Class<? extends Job> getJobClass() {
        return this.getClass();
    }

    public JobKey getJobKey() {
        return new JobKey("job_" + this.getClass().getSimpleName(), "group_" + this.getClass().getSimpleName());
    }

    //"10/5 * * * * ?"; // 每分钟的10s起，每5s触发任务
    public String getCronExpression() {
        return null;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
