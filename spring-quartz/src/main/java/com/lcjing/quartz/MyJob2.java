package com.lcjing.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lcjing
 * @date 2020/08/10
 */
public class MyJob2 implements Job {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        log.info(dateTimeFormatter.format(currentTime) + "--->MyJob2 执行");
    }

}
