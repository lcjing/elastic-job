package com.elastic.util;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.internal.schedule.JobRegistry;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.elastic.simple.MySimpleJob;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/***
 * elastic-job 定时调度工具
 * @author lcjing
 * @date 2020/08/11
 *
 */
public class JobService {

    private static final Logger log = LoggerFactory.getLogger(Job.class);

    /**
     * 配置Zookeeper
     *
     * @return
     */
    public CoordinatorRegistryCenter makeRegistryCenter() {
        String ipAddr = "localhost:2182";
        ZookeeperConfiguration config = new ZookeeperConfiguration(ipAddr, "elastic_job");
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(config);
        return regCenter;
    }

    /***
     * Zookeeper,调度配置
     * @return
     */
    private CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = makeRegistryCenter();
        //调度开启
        regCenter.init();
        return regCenter;
    }

    /***
     * 作业配置
     *
     * @param jobName    作业名称
     * @param cycleTime    执行时间周期
     * @param canonicalName 事件类型(例:备份,ntp,邮件发送) ---> MySimpleJob.class.getCanonicalName()
     * @return
     */
    private LiteJobConfiguration createJobConfiguration(String jobName, String cycleTime, String canonicalName) {
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder(jobName, cycleTime, 1).build();

        //不同的作业内容,进入不同的实现类.参数二为作业实现类
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, canonicalName);

        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build();
        return simpleJobRootConfig;
    }

    /***
     * 添加作业
     *
     * @param jobName    作业名称
     * @param cycleTime    执行时间周期
     * @param eventType 事件类型(例:备份,ntp,邮件发送)
     */
    public void addJob(String jobName, String cycleTime, String eventType) {
        log.info("开始添加定时任务, 任务名: {}", jobName);
        CoordinatorRegistryCenter regCenter = createRegistryCenter();
        LiteJobConfiguration jobConfig = createJobConfiguration(jobName, cycleTime, eventType);
        new JobScheduler(regCenter, jobConfig).init();
        log.info("添加任务 {} 结束!", jobName);

    }

    /***
     * 删除作业
     * @param jobName    作业名称
     */
    public void deleteJob(String jobName) {

        CoordinatorRegistryCenter regCenter = makeRegistryCenter();
        regCenter.init();
        String node = "/" + jobName;
        if (regCenter.isExisted(node)) {
            log.info("开始删除任务, 任务名: {}", jobName);
            regCenter.remove("/" + jobName);
        }

    }

    /***
     * 设置任务立即执行
     * @param jobName 任务名
     */
    public void triggerJob(String jobName) {
        JobRegistry.getInstance().getJobScheduleController(jobName).triggerJob();
    }
}
