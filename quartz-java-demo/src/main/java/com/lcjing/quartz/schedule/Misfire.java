package com.lcjing.quartz.schedule;

import com.lcjing.quartz.job.MyJob2;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 用来测试misfire
 *
 * @author lcjing
 * @date 2020/08/10
 */
public class Misfire {
    public static void main(String[] args) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(MyJob2.class)
                .withIdentity("job2", "group1")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().
                        withMisfireHandlingInstructionNowWithExistingCount().
                        withIntervalInSeconds(1).
                        repeatForever())
                .build();

        // Trigger2 占用掉5个线程，只占用一次
        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().
                        withIntervalInMilliseconds(1).
                        withRepeatCount(5))
                .build();

        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        //scheduler.scheduleJob(jobDetail, trigger);
        scheduler.scheduleJob(jobDetail, trigger2);
        scheduler.start();
    }
}
