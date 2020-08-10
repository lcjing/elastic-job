package com.lcjing.demo.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author lcjing
 * @date 2020/08/10
 */
public interface BaseJob extends Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException;
}

