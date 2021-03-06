package com.elastic.dataflow;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author lcjing
 * @date 2020/08/11
 */
public class MyDataFlowJob implements DataflowJob<String> {
    public List<String> fetchData(ShardingContext shardingContext) {
        System.out.println("开始获取数据");
        // 假装从文件或者数据库中获取到了数据
        Random random = new Random();
        if (random.nextInt() % 3 != 0) {
            return null;
        }
        return Arrays.asList("111", "222", "333");
    }

    public void processData(ShardingContext shardingContext, List<String> data) {
        for (String val : data) {
            // 处理完数据要移除掉，不然就会一直跑
            System.out.println("开始处理数据：" + val);
        }

    }
}
