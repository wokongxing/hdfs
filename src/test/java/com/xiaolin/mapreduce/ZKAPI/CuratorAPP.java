package com.xiaolin.mapreduce.ZKAPI;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CuratorAPP {
    public CuratorFramework client=null;
    private static  String zkServerIps="114.67.98.145:2181";//zkip
    private static Logger logger = LoggerFactory.getLogger(ZooKeeperApp.class);

    @Test
    public void  testCuratorApp() throws Exception {
        //创建节点
//        client.create().withMode(CreateMode.PERSISTENT)
//            .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
//            .forPath("/xiaolin/001","测试".getBytes());
    }

    @After
    public void closeClient(){
        if (client!=null){
            client.close();
        }
    }
    @Before
    public void init(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,5);
        client = CuratorFrameworkFactory.builder()// 使用工厂类来建造客户端的实例对象
                .connectString(zkServerIps) // 放入zookeeper服务器ip
                .sessionTimeoutMs(5000).retryPolicy(retryPolicy)// 设定会话时间以及重连策略
                .namespace("Curator001")//命名空间
                .build();//建立连接通道
//        client = CuratorFrameworkFactory.newClient(zkServerIps,5000,3000,new ExponentialBackoffRetry(1000,5) );
        //启动Curator
        client.start();
        logger.warn("Client客户端的连接状态:"+client.getState());
    }

}
