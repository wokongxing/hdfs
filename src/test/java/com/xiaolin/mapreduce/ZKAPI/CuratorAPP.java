package com.xiaolin.mapreduce.ZKAPI;

import com.sun.org.apache.bcel.internal.generic.SWITCH;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CuratorAPP {
    public CuratorFramework client=null;
    private static  String zkServerIps="114.67.98.145:2181";//zkip
    private static Logger logger = LoggerFactory.getLogger(ZooKeeperApp.class);

    @Test
    public void  testCuratorApp() throws Exception {
        //创建节点
//        client.create().withMode(CreateMode.PERSISTENT)
//            .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
//            .forPath("/xiaolin01","测试".getBytes());
        //创建临时节点
//        client.create().withMode(CreateMode.EPHEMERAL)
//                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
//                .forPath("/xiaolin-eph","data".getBytes());
//        Thread.sleep(10000);
        //异步创建节点
//        client.create()
//                .withMode(CreateMode.PERSISTENT)
//                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
//                .inBackground(new BackgroundCallback() {
//                    @Override
//                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
//                        logger.warn("异步返回的name:"+curatorEvent.getName());
//                        logger.warn("异步返回的path:"+curatorEvent.getPath());
//                        logger.warn("异步返回的context:"+curatorEvent.getContext());
//                        logger.warn("异步返回的type:"+curatorEvent.getType());
//                        logger.warn("异步返回的code:"+curatorEvent.getResultCode());
//                    }
//                },"传入的参数")
//                .forPath("/xioalin02","data".getBytes());
//        Thread.sleep(1000);

        //创建多级节点
//        client.create().creatingParentsIfNeeded()
//                .withMode(CreateMode.PERSISTENT)
//                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
//                .forPath("/xiaolin03/a/b/c","aaa".getBytes());
        //读取数据并获得Stat信息
//        Stat stat = new Stat();
//        byte[] data = client.getData().storingStatIn(stat).forPath("/xiaolin03");
//        System.out.println("数据:"+new String(data));
//        System.out.println("版本:"+stat.getVersion());

        //判断是否存在
//        Stat stat = client.checkExists().forPath("/xiaolin03");
//        if (stat!=null){
//            logger.warn("节点存在");
//            logger.warn("节点信息"+stat.getNumChildren());
//        }else {
//            logger.warn("节点不存在");
//        }
        //修改数据
//        client.setData().forPath("/xiaolin03","data2".getBytes());
        //设置数据，并指定版本
//        client.setData().withVersion(-1).forPath("/xiaolin03","data3".getBytes());

        //删除节点
//        client.delete().forPath("/xioalin02");

        //获取子节点
//        List<String> list = client.getChildren().forPath("/xiaolin03");
//        for (String children :list){
//            logger.warn("节点名字:"+children);
//        }

        //监听事件
        TreeCache treeCache = new TreeCache(client, "/xiaolin01");
        treeCache.start();
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                ChildData childData = treeCacheEvent.getData();
                if (childData==null){
                    logger.warn("数据为空的");
                    return;
                }
                String path = childData.getPath();
                switch (treeCacheEvent.getType()){
                    case NODE_ADDED:
                        logger.warn("节点的增加--节点路径:"+path+"节点数据:"+new String(childData.getData(),"utf-8"));
                        break;
                    case NODE_UPDATED:
                        logger.warn("节点发生修改--节点路径:"+path+"节点数据:"+new String(childData.getData(),"utf-8"));
                        break;
                    case NODE_REMOVED:
                        logger.warn("节点被移除--节点路径:"+path);
                        default:
                            logger.warn("其他变化:"+treeCacheEvent.getType().toString());
                            break;
                }
            }
        });
        Thread.sleep(50000);

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
