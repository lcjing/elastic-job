package com.elastic.dataflow;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * @author lcjing
 * @date 2020/08/11
 */
public class DataFlowJobTest {
    public static void main(String[] args) {
        // ZK注册中心
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("localhost:2182", "elastic-job-standalone"));
        regCenter.init();

        // 定义作业核心配置
        JobCoreConfiguration dataJobCoreConfig = JobCoreConfiguration.newBuilder("MyDataFlowJob", "0/4 * * * * ?", 2).build();
        // 定义DATAFLOW类型配置
        DataflowJobConfiguration dataJobConfig = new DataflowJobConfiguration(dataJobCoreConfig, MyDataFlowJob.class.getCanonicalName(), true);

        LiteJobConfiguration dataflowJobRootConfig = LiteJobConfiguration.newBuilder(dataJobConfig).build();
        // 构建Job
        new JobScheduler(regCenter, dataflowJobRootConfig).init();
    }

}
