package com.xiaolin.mapreduce.ZKAPI;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * 讲师：PK哥
 */
public class ZooKeeperApp implements Watcher{

    private static Logger logger = LoggerFactory.getLogger(ZooKeeperApp.class);

    private static CountDownLatch connected = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        ZooKeeper zk = new ZooKeeper("hadoop001:2181", 5000, new ZooKeeperApp());


        logger.warn("客户端开始连接ZK服务器...");
        logger.warn("连接状态:{}", zk.getState());

        connected.await();
       Thread.sleep(2000);

        logger.warn("连接状态:{}", zk.getState());

        // 创建节点
//        String path1 = zk.create("/zk-test-eph", "1".getBytes(),
//                ZooDefs.Ids.OPEN_ACL_UNSAFE,
//                CreateMode.EPHEMERAL);
//        logger.warn("创建节点成功:{}", path1);
//
//        Thread.sleep(10000);


        // 异步创建节点

//        String ctx = "{'create':'success'}";
//
//        zk.create("/zk-test-eph", "1".getBytes(),
//                ZooDefs.Ids.OPEN_ACL_UNSAFE,
//                CreateMode.EPHEMERAL,
//                new AsyncCallback.StringCallback() {
//                    @Override
//                    public void processResult(int i, String path, Object ctx, String name) {
//                        logger.warn("创建节点成功:{}", name);
//                    }
//                },ctx);

//        Stat stat = zk.setData("/ruoze",
//                "ruozedata".getBytes(),
//                -1);
//        logger.warn("版本号: {}", stat.getVersion());


//        zk.delete("/ruoze/abc",-1);

//        String path1 = zk.create("/zk-test-eph/a/b/c", "1".getBytes(),
//                ZooDefs.Ids.OPEN_ACL_UNSAFE,
//                CreateMode.PERSISTENT);
//        logger.warn("创建节点成功:{}", path1);

//        Stat stat = new Stat();
//        byte[] data = zk.getData("/xioalin", true, stat);
//        logger.warn("获取节点数据:{}", new String(data));
//        logger.warn("节点版本号:{}", stat.getVersion());


//        Stat stat = zk.exists("/ruo1ze",true);
//        if(null != stat) {
//            logger.warn("节点存在");
//        } else {
//            logger.warn("节点不存在");
//        }

//        List<String> children = zk.getChildren("/ruoze", true);
//        for(String child : children) {
//            logger.warn(child);
//        }
//
//        Thread.sleep(10000);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            logger.warn("接收到watch通知:{}", watchedEvent);
            connected.countDown();
        }
    }
}
