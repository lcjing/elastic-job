package com.lcjing.config;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.lcjing.job.MyDataFlowJob;
import com.lcjing.job.MySimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lcjing
 * @date 2020/08/11
 */
@Configuration
public class ElasticJobConfig {
    @Autowired
    private ZookeeperRegistryCenter regCenter;

    /**
     * 定义调度器
     *
     * @param simpleJob              job
     * @param cron                   执行周期
     * @param shardingTotalCount     分片数
     * @param shardingItemParameters 分片参数
     * @param jobParameter           任务参数
     * @return schedule
     */
    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final MySimpleJob simpleJob,
                                           @Value("${simpleJob.cron}") final String cron,
                                           @Value("${simpleJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${simpleJob.shardingItemParameters}") final String shardingItemParameters,
                                           @Value("${simpleJob.jobParameter}") final String jobParameter) {
        return new SpringJobScheduler(simpleJob, regCenter,
                getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters, jobParameter));
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters,
                                                         final String jobParameter) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                        .shardingItemParameters(shardingItemParameters).jobParameter(jobParameter).build()
                , jobClass.getCanonicalName())
        ).overwrite(true).build();
    }

    /**
     * @param dataFlowJob            job
     * @param cron                   执行周期
     * @param shardingTotalCount     分片个数
     * @param shardingItemParameters 分片周期
     * @return shedule
     */
    @Bean(initMethod = "init")
    public JobScheduler dataFlowJobScheduler(final MyDataFlowJob dataFlowJob,
                                             @Value("${dataflowJob.cron}") final String cron,
                                             @Value("${dataflowJob.shardingTotalCount}") final int shardingTotalCount,
                                             @Value("${dataflowJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(dataFlowJob, regCenter, getDataFlowJobConfiguration(dataFlowJob.getClass(), cron, shardingTotalCount, shardingItemParameters, true));
    }

    private LiteJobConfiguration getDataFlowJobConfiguration(final Class<? extends DataflowJob> jobClass,
                                                             final String cron,
                                                             final int shardingTotalCount,
                                                             final String shardingItemParameters,
                                                             //是否是流式作业
                                                             final Boolean streamingProcess) {
        return LiteJobConfiguration.newBuilder(new DataflowJobConfiguration(
                JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                        .shardingItemParameters(shardingItemParameters).build()
                // true为流式作业 ,除非fetchData返回数据为null或者size为0, 否则会一直执行
                // false非流式,只会按配置时间执行一次
                , jobClass.getCanonicalName(), streamingProcess)
        ).overwrite(true).build();
    }

    /**
     * @param cron                   执行周期
     * @param shardingTotalCount     分片个数
     * @param shardingItemParameters 分片参数
     * @return schedule
     */
    @Bean(initMethod = "init")
    public JobScheduler scriptJobScheduler(@Value("${scriptJob.cron}") final String cron,
                                           @Value("${scriptJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${scriptJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(null, regCenter,
                getScriptJobConfiguration("script_job", cron, shardingTotalCount, shardingItemParameters, "D:/1.bat"));
    }

    private LiteJobConfiguration getScriptJobConfiguration(final String jobName,
                                                           final String cron,
                                                           final int shardingTotalCount,
                                                           final String shardingItemParameters,
                                                           //是脚本路径或者命令
                                                           final String scriptCommandLine) {
        return LiteJobConfiguration.newBuilder(new ScriptJobConfiguration(
                JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                        .shardingItemParameters(shardingItemParameters).build()
                // 此处配置文件路径或者执行命令
                , scriptCommandLine)
        ).overwrite(true).build();
    }

    /**
     *   动态添加任务
     */
    public void addSimpleJobScheduler(final Class<? extends SimpleJob> jobClass,
                                       final String cron,
                                       final int shardingTotalCount,
                                       final String shardingItemParameters){
        JobCoreConfiguration coreConfig = JobCoreConfiguration
                .newBuilder(jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters)
                .jobParameter("custom-job")
                .build();
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(coreConfig, jobClass.getCanonicalName());
        JobScheduler jobScheduler = new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(simpleJobConfig).build());
        jobScheduler.init();
    }

}
