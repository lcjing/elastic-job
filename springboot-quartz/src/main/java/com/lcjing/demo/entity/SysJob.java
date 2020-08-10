package com.lcjing.demo.entity;

import java.io.Serializable;

/**
 * 任务调度类
 *
 * @author lcjing
 * @date 2020/08/10
 */
public class SysJob implements Serializable {

    private static final long serialVersionUID = 3746569356086283114L;
    /**
     * 任务ID
     */

    private Integer id;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务组别
     */
    private String jobGroup;
    /**
     * 任务表达式
     */
    private String jobCron;
    /**
     * 类路径
     */
    private String jobClassPath;
    /**
     * 任务状态 1启用 0停用
     */
    private Integer jobStatus;
    /**
     * 任务状态 1启用 0停用
     */
    private String jobStatusStr;
    /**
     * 任务具体描述
     */
    private String jobDescribe;
    /**
     * 传递map参数
     */
    private String jobDataMap;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobDescribe() {
        return jobDescribe;
    }

    public void setJobDescribe(String jobDescribe) {
        this.jobDescribe = jobDescribe == null ? null : jobDescribe.trim();
    }

    public String getJobClassPath() {
        return jobClassPath;
    }

    public void setJobClassPath(String jobClassPath) {
        this.jobClassPath = jobClassPath == null ? null : jobClassPath.trim();
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobStatusStr() {
        if ("1".equals(jobStatus.toString())) {
            return "运行中";
        } else if ("0".equals(jobStatus.toString())) {
            return "已停止";
        } else {
            return "未知";
        }

    }

    public void setJobStatusStr(String jobStatusStr) {
        this.jobStatusStr = jobStatusStr;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron == null ? null : jobCron.trim();
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup == null ? null : jobGroup.trim();
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobDataMap() {
        return jobDataMap;
    }

    public void setJobDataMap(String jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

}