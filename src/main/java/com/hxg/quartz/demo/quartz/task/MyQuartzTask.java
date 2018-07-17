package com.hxg.quartz.demo.quartz.task;

import com.hxg.quartz.demo.quartz.service.HelloService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class MyQuartzTask extends BaseTask {

    @Autowired
    private HelloService helloService;


    @Override
//	public Class<? extends Job> getJobClass() {
//		return this.getClass();
//	}

//	public JobKey getJobKey(){
//		return new JobKey("MyTask_job", "MyTask_group");
//	}

    public String getCronExpression() {
        helloService.hello();
        return "0/3 * * * * ?";
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        System.out.println("MyTask.execute().date=" + new Date());
        helloService.hello();
    }

}
