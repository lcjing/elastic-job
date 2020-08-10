package com.lcjing.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lcjing
 * @date 2020/08/10
 */
//@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail printTimeJobDetail(){
        return JobBuilder.newJob(MyJob1.class)
                .withIdentity("my_job_01")
                .usingJobData("name", "quartz")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger printTimeJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(printTimeJobDetail())
                .withIdentity("my_trigger_01")
                .withSchedule(cronScheduleBuilder)
                .build();
    }

}
