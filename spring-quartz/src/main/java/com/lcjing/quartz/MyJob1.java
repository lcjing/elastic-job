package com.lcjing.quartz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 需要执行的任务必须实现 Job接口
 * 如果是MethodInvokingJobDetailFactoryBean方式产生Job，则不用实现Job接口
 * @author lcjing
 * @date 2020/08/10
 */
public class MyJob1 implements Job{

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime currentTime = LocalDateTime.now();
		log.info(dateTimeFormatter.format(currentTime) + "--->MyJob1 执行");
	}

}
