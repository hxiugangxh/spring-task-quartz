package com.hxg.quartz.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Quartz配置类
 *
 * @author Angel -- 守护天使
 * @version v.0.1
 * @date 2017年5月17日
 */
@Configuration
public class QuartzConfig {

    @Autowired
    private SpringBeanJobFactory springJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        //把Job交给Spring来管理，这样Job就能使用由Spring产生的Bean了
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(springJobFactory);
        return schedulerFactoryBean;
    }

//  @Bean
//  public Scheduler scheduler() {
//    return schedulerFactoryBean().getScheduler();
//  }
}
