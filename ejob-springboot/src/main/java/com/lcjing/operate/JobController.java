package com.lcjing.operate;

import com.lcjing.config.ElasticJobConfig;
import com.lcjing.job.MySimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lcjing
 * @date 2020/08/11
 */
public class JobController {
    @Autowired
    private ElasticJobConfig elasticJobConfig;

    @RequestMapping("/addJob")
    public void addJob() {
        int shardingTotalCount = 2;
        elasticJobConfig.addSimpleJobScheduler(new MySimpleJob().getClass(),"0/2 * * * * ?",shardingTotalCount,"0=A,1=B");
    }


}
