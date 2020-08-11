package com.elastic.script;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * @author lcjing
 * @date 2020/08/11
 */
public class ScriptJobTest {
    public static void main(String[] args) {
        // ZK注册中心
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("localhost:2182", "elastic-job-standalone"));
        regCenter.init();

        // 定义作业核心配置
        JobCoreConfiguration scriptJobCoreConfig = JobCoreConfiguration.newBuilder("MyScriptJob", "0/4 * * * * ?", 2).build();
        // 定义SCRIPT类型配置
        ScriptJobConfiguration scriptJobConfig = new ScriptJobConfiguration(scriptJobCoreConfig, "D:/1.bat");


        LiteJobConfiguration scriptJobRootConfig = LiteJobConfiguration.newBuilder(scriptJobConfig).build();
        // 构建Job
        new JobScheduler(regCenter, scriptJobRootConfig).init();
    }

}
