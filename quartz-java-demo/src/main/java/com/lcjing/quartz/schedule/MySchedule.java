package com.lcjing.quartz.schedule;

import com.lcjing.quartz.job.MyJob1;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Quartz 任务调度
 *
 * @author lcjing
 * @date 2020/08/10
 */
public class MySchedule {
    /**
     * 每5秒执行一次
     */
    private static final String cronExpression = "0/5 * * * * ?";

    public static void main(String[] args) throws SchedulerException {
        //1、创建 JobDetail
        JobDetail jobDetail = JobBuilder.newJob(MyJob1.class)
                .withIdentity("job1", "group1")
                .usingJobData("name", "hello, quartz")
                .usingJobData("value", "come on!")
                .build();

        //2、创建 Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing())
                .build();

        //3、创建 Schedule
        StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        //4、绑定关系
        scheduler.scheduleJob(jobDetail, trigger);

        //5、启动任务
        scheduler.start();


    }
}
