package com.lcjing.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 实现自己的定时任务
 *
 * @author lcjing
 * @date 2020/08/10
 */
public class MyJob1 implements Job {
    /**
     * 具体的执行任务
     * @param context context
     * @throws JobExecutionException exception
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        System.out.println(dateTimeFormatter.format(currentTime) + " MyJob1 任务执行了: "
                + jobDataMap.getString("name") + " ---> " + jobDataMap.getString("value"));
    }
}
