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
public class ZookeeperHelper implements Watcher {
    private static ZooKeeper zooKeeper;
    private static int sessionTimeOut = 2000;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private static String host = "192.168.81.72:2181";

    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){

        }
    }

    public static void connectZookeeper() throws IOException {
        zooKeeper = new ZooKeeper(host,sessionTimeOut,null);

    }
}
