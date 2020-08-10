package com.lcjing.quartz.listener;

import com.lcjing.quartz.job.MyJob1;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;

/**
 * @author lcjing
 * @date 2020/08/10
 */
public class MyJobListenerTest {
    public static void main(String[] args) throws SchedulerException {

        // JobDetail
        JobDetail jobDetail = JobBuilder.newJob(MyJob1.class).withIdentity("job1", "group1").build();

        // Trigger
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();

        // SchedulerFactory
        SchedulerFactory factory = new StdSchedulerFactory();

        // Scheduler
        Scheduler scheduler = factory.getScheduler();

        scheduler.scheduleJob(jobDetail, trigger);

        // 创建并注册一个全局的Job Listener
        scheduler.getListenerManager().addJobListener(new MyJobListener(), EverythingMatcher.allJobs());

        scheduler.start();

    }

}
