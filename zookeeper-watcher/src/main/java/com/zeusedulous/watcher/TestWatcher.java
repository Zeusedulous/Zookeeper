package com.zeusedulous.watcher;

import org.apache.zookeeper.CreateMode;

/**
 * @Author : Zeusedulous
 * @Date : 2020/6/16 15:02
 * @Desc :
 */
public class TestWatcher {
    public static void main(String[] args) throws Exception {
        ZookeeperHelper zk = new ZookeeperHelper();
        zk.getZooKeeper();
        zk.createNode("/test3","test3_value", CreateMode.PERSISTENT);
        zk.setData("/test3","test33_value3".getBytes());
        Thread.sleep(300000);
        zk.deleteNode("/test3");

    }
}
