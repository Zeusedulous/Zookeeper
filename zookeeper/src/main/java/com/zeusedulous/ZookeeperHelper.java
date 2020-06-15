package com.zeusedulous;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Author : Zeusedulous
 * @Date : 2020/6/15 14:44
 * @Desc :
 */
public class ZookeeperHelper {
    private static ZooKeeper zooKeeper;
    private int sessionTimeOut = 3000;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private static String host = "dzjtest.linkdood.cn:2181";

    public ZooKeeper connectZookeeper() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(host, sessionTimeOut, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
                    System.out.println("connect successful");
                    countDownLatch.countDown();
                }else{
                    System.out.println("connect successful");
                }
            }
        });
        countDownLatch.await();
        return zooKeeper;
    }
}
